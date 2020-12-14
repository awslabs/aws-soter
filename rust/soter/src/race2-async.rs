// Main program.

pub mod soter;

#[tokio::main]
async fn main() -> Result<(), Box<dyn std::error::Error>> {
    // Initialize soter.
    let soter_server = soter::server::start().await;

    // Body of the main program.
    println!("Hello, I have started and now I'm about to exit.");

    // Race condition 2.
    race_condition_2().await;
  
    // Terminate soter.
    soter::server::stop(soter_server).await;

    Ok(())
}

// Taken from https://doc.rust-lang.org/nomicon/races.html
//  - gRPC library needs async move, not supported in Rust, had to convert
//    the example to tokio::spawn from thread::spawn.
async fn race_condition_2() -> () {
    println!("Running race condition 2...");

    use std::sync::atomic::{AtomicUsize, Ordering};
    use std::sync::Arc;

    let data = vec![1, 2, 3, 4];
    
    let idx = Arc::new(AtomicUsize::new(0));
    let other_idx = idx.clone();
   
    // `move` captures other_idx by-value, moving it into this thread
    soter::tokio::spawn(async move {
        println!("Thread executing...");
        
        // It's ok to mutate idx because this value
        // is an atomic, so it can't cause a Data Race.
        other_idx.fetch_add(10, Ordering::SeqCst);

        println!("Thread finishing.");
    }).await;
   
    println!("-------------------------------");
    println!("index: {}", idx.load(Ordering::SeqCst));
    if idx.load(Ordering::SeqCst) < data.len() {
        // Context switch required here to allow the thread to increment the counter after the check.
        soter::tokio::task::yield_now().await;

        unsafe {
            println!("About to read value!");
            // Incorrectly loading the idx after we did the bounds check.
            // It could have changed. This is a race condition, *and dangerous*
            // because we decided to do `get_unchecked`, which is `unsafe`.
            let index = idx.load(Ordering::SeqCst);
            println!("index: {}", index);
            println!("{}", data.get_unchecked(index)); // undefined behavior

            if index == 10 {
                std::process::exit(1);
            }
        }
    };

    // TODO: thread hasn't run yet?  it is cancelled.  figure out how to handle this.
    // drop(reference)
    // for now, we'll just cycle with some yields.
    for _x in 0..10 {
        soter::tokio::task::yield_now().await;
    }
}
// Main program.

pub mod soter;

#[tokio::main]
async fn main() -> Result<(), Box<dyn std::error::Error>> {
    let soter_server = soter::server::start().await;

    // Body of the main program.
    println!("Hello, I have started and now I'm about to exit.");

    // Race condition 1.
    race_condition_1().await;
   
    soter::server::stop(soter_server).await;

    Ok(())
}

// Taken from: https://doc.rust-lang.org/nomicon/races.html
async fn race_condition_1() -> () {
    println!("Running race condition 1...");

    use std::sync::atomic::{AtomicUsize, Ordering};
    use std::sync::Arc;

    let data = vec![1, 2, 3, 4];
    // Arc so that the memory the AtomicUsize is stored in still exists for
    // the other thread to increment, even if we completely finish executing
    // before it. Rust won't compile the program without it, because of the
    // lifetime requirements of thread::spawn!
    let idx = Arc::new(AtomicUsize::new(0));
    let other_idx = idx.clone();

    // `move` captures other_idx by-value, moving it into this thread
    soter::tokio::spawn(async move {
        // It's ok to mutate idx because this value
        // is an atomic, so it can't cause a Data Race.
        other_idx.fetch_add(10, Ordering::SeqCst);
    }).await;

    // Index with the value loaded from the atomic. This is safe because we
    // read the atomic memory only once, and then pass a copy of that value
    // to the Vec's indexing implementation. This indexing will be correctly
    // bounds checked, and there's no chance of the value getting changed
    // in the middle. However our program may panic if the thread we spawned
    // managed to increment before this ran. A race condition because correct
    // program execution (panicking is rarely correct) depends on order of
    // thread execution.
    let index = idx.load(Ordering::SeqCst);
    println!("index: {}", index);
    let data = data[index];
    println!("{}", data);

    // TODO: thread hasn't run yet?  it is cancelled.  figure out how to handle this.
    // drop(reference)
    // for now, we'll just cycle with some yields.
    for _x in 0..10 {
        soter::tokio::task::yield_now().await;
    }
}
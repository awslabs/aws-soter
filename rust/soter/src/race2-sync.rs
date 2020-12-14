// Main program.

pub mod soter;

fn main() -> () {
    race_condition_2();
    ()
}

// Taken from https://doc.rust-lang.org/nomicon/races.html
fn race_condition_2() -> () {
    println!("Running race condition 2...");

    use std::sync::atomic::{AtomicUsize, Ordering};
    use std::sync::Arc;

    let data = vec![1, 2, 3, 4];
    
    let idx = Arc::new(AtomicUsize::new(0));
    let other_idx = idx.clone();
   
    // `move` captures other_idx by-value, moving it into this thread
    soter::thread::spawn(move || {
        // It's ok to mutate idx because this value
        // is an atomic, so it can't cause a Data Race.
        other_idx.fetch_add(10, Ordering::SeqCst);
    });
   
    println!("-------------------------------");
    println!("index: {}", idx.load(Ordering::SeqCst));
    if idx.load(Ordering::SeqCst) < data.len() {
        // Context switch required here to allow the thread to increment the counter after the check.
        soter::thread::yield_now();

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
}
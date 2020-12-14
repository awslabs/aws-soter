use std::thread;
pub fn spawn<F, T>(f: F) -> crate::soter::thread::JoinHandle<T>
where
    F: FnOnce() -> T,
    F: Send + 'static,
    T: Send + 'static,
{
    println!("Task creation starting...");
    let id = std::thread::spawn(move || { crate::soter::sync_client::create_task().unwrap() }).join().unwrap();
    let start_id = id.clone();
    let yield_id = id.clone();
    let join_id = id.clone();
    let block_id = id.clone();
    let exit_id = id.clone();
    println!("Task created: {}", id);

    // Spawn the task.
    let thread = thread::spawn(move || {
        // Once this task spawns, block immediately to prevent it from starting 
        //  before Soter picks it.
        std::thread::spawn(move || {
            crate::soter::sync_client::block_task(&block_id).unwrap();
        }).join().unwrap();

        // Run the actual code and wait for the result
        // TODO: can we do this without an additional thread?  not sure how yet.
        let x = thread::spawn(f).join().unwrap();

        // Soter exit task.
        std::thread::spawn(move || {
            crate::soter::sync_client::exit_task(&exit_id).unwrap();
        }).join().unwrap();

        // Return the result.
        x
    });

    // Start task.
    std::thread::spawn(move || {
        crate::soter::sync_client::start_task(&start_id).unwrap();
    }).join().unwrap();

    // Yield to allow for scheduling of new task.
    std::thread::spawn(move || {
        crate::soter::sync_client::yield_task(&yield_id, crate::soter::SoterUpdateType::NoOp, &yield_id).unwrap();
    }).join().unwrap();

    // Return the join handle.
    crate::soter::thread::JoinHandle { join_handle: thread, id: join_id }
}

pub struct JoinHandle<T> {
    pub join_handle: std::thread::JoinHandle<T>,
    pub id: String,
}

impl<T> Drop for JoinHandle<T> {
    fn drop(&mut self) {
        println!("> Dropping JoinHandle: {}", self.id);
        let id_ref = self.id.clone();
        std::thread::spawn(move || {
            crate::soter::sync_client::cancel_task(&id_ref).unwrap();
        }).join().unwrap();
        println!("> Done cancelling the task: {}", self.id);
    }
}

pub fn yield_now() {
    // TODO: Dummy identifer for now, don't actually need one by proto definition currently requires it.
    let yield_id = String::from("1");

    // Soter yield to allow for scheduling.
    std::thread::spawn(move || {
        crate::soter::sync_client::yield_task(&yield_id, crate::soter::SoterUpdateType::NoOp, &yield_id).unwrap();
    }).join().unwrap();
}
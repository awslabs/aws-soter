use std::future::Future;

pub async fn spawn<T>(task: T) -> crate::soter::tokio::task::JoinHandle<T::Output>
where
    T: Future + Send + 'static,
    T::Output: Send + 'static,
{
    let id = crate::soter::client::create_task().await.unwrap();
    let start_id = id.clone();

    // Spawn the task.
    // TODO: This can't be without async move, I guess?  Needs async move to caputre id for lifetie.
    let thread = tokio::spawn(async move {
        // Once this task spawns, block immediately to prevent it from starting 
        //  before Soter picks it.
        crate::soter::client::block_task(&id).await.unwrap();

        // Run the actual code and wait for the result
        // TODO: can we do this without an additional thread?  not sure how yet.
        let x = tokio::spawn(task).await.unwrap();

        // Soter exit task.
        crate::soter::client::exit_task(&id).await.unwrap();

        // Return the result.
        x
    });

    crate::soter::sync_client::start_task(&start_id).unwrap();

    // Tokio yield.
    tokio::task::yield_now().await;

    // Soter yield.
    crate::soter::tokio::task::yield_now().await;

    // Return the join handle.
    crate::soter::tokio::task::JoinHandle { join_handle: thread, id: start_id }
}

pub mod task {
    pub struct JoinHandle<T> {
        pub id: String,
        pub join_handle: tokio::task::JoinHandle<T>
    }

    impl<T> Drop for JoinHandle<T> {
        fn drop(&mut self) {
            println!("> Dropping JoinHandle: {}", self.id);
            // TODO: We don't seem to need to call drop on the individual elements in the struct,
            //       It seems like this is happening automatically?
        }
    }

    pub async fn yield_now() {
        // TODO: Dummy identifer for now, don't actually need one by proto definition currently requires it.
        let id = String::from("1");

        // Tokio yield.
        tokio::task::yield_now().await;

        // Soter yield to allow for scheduling.
        crate::soter::client::yield_task(&id, crate::soter::SoterUpdateType::NoOp, &id).await.unwrap();
    }
}
mod soter_service {
    tonic::include_proto!("soter");
}

use once_cell::sync::OnceCell;

use std::sync::atomic::Ordering;

use soter_service::soter_service_client::SoterServiceClient;

use soter_service::{CreateTaskRequest, 
                    ExitTaskRequest, 
                    AbnormalExitTaskRequest,
                    YieldTaskRequest,
                    BlockTaskRequest,
                    CancelTaskRequest,
                    StartTaskRequest,
                    UpdateStateRequest,
                    NondeterministicBooleanChoiceRequest,
                    NondeterministicIntegerChoiceRequest,
                    NondeterministicIntegerWithBoundChoiceRequest};

use crate::soter::SoterUpdateType;
use crate::soter::update_type_to_i32;

static CLIENT: OnceCell<soter_service::soter_service_client::SoterServiceClient<tonic::transport::channel::Channel>> = OnceCell::new();
static CLIENT_INITIALIZED: OnceCell<tokio::sync::Mutex<bool>> = OnceCell::new();

pub async fn create_task() -> Result<String, Box<dyn std::error::Error>> {
    let task_id = crate::soter::NEXT_TASK_ID.fetch_add(1, Ordering::SeqCst);
    let id : String = task_id.to_string();
    let return_id = id.clone();

    // Establish connections.
    if let Some(client) = get_client().await {
        // Clone client.
        let mut cloned_client = client.clone();

        // Register task with Soter.
        let request = tonic::Request::new(
            CreateTaskRequest {
                id: id
            }
        );
        println!("Issuing create task...");
        let response = cloned_client.create_task(request).await?.into_inner();
        println!("createTask = {:?}", response);
    } else {
        panic!("There was a problem!");
    }

    println!("Returning id {}", return_id);
    Ok(return_id)
}

pub async fn cancel_task(id: &str) -> Result<(), Box<dyn std::error::Error>> {
    // Establish connections.
    if let Some(client) = get_client().await {
        // Clone client.
        let mut cloned_client = client.clone();

        // Register task with Soter.
        let request = tonic::Request::new(
            CancelTaskRequest {
                id: id.to_string()
            }
        );
        let response = cloned_client.cancel_task(request).await?.into_inner();
        println!("cancelTask = {:?}", response);
    } else {
        panic!("There was a problem!");
    }

    Ok(())
}

pub async fn exit_task(id: &str) -> Result<(), Box<dyn std::error::Error>> {
    // Establish connections.
    if let Some(client) = get_client().await {
        // Clone client.
        let mut cloned_client = client.clone();

        // Register task with Soter.
        let request = tonic::Request::new(
            ExitTaskRequest {
                id: id.to_string()
            }
        );
        let response = cloned_client.exit_task(request).await?.into_inner();
        println!("exitTask = {:?}", response);
    } else {
        panic!("There was a problem!");
    }

    Ok(())
}

pub async fn abnormal_exit_task(id: &str) -> Result<(), Box<dyn std::error::Error>> {
    // Establish connections.
    if let Some(client) = get_client().await {
        // Clone client.
        let mut cloned_client = client.clone();

        // Register task with Soter.
        let request = tonic::Request::new(
            AbnormalExitTaskRequest {
                id: id.to_string()
            }
        );
        let response = cloned_client.abnormal_exit_task(request).await?.into_inner();
        println!("abnormalExitTask = {:?}", response);
    } else {
        panic!("There was a problem!");
    }

    Ok(())
}

pub async fn block_task(id: &str) -> Result<(), Box<dyn std::error::Error>> {
    // Establish connections.
    if let Some(client) = get_client().await {
        // Clone client.
        let mut cloned_client = client.clone();

        // Register task with Soter.
        let request = tonic::Request::new(
            BlockTaskRequest {
                id: id.to_string()
            }
        );
        let response = cloned_client.block_task(request).await?.into_inner();
        println!("blockTask = {:?}", response);
    } else {
        panic!("There was a problem!");
    }

    Ok(())
}

pub async fn start_task(id: &str) -> Result<(), Box<dyn std::error::Error>> {
    // Establish connections.
    if let Some(client) = get_client().await {
        // Clone client.
        let mut cloned_client = client.clone();

        // Register task with Soter.
        let request = tonic::Request::new(
            StartTaskRequest {
                id: id.to_string()
            }
        );
        let response = cloned_client.start_task(request).await?.into_inner();
        println!("startTask = {:?}", response);
    } else {
        panic!("There was a problem!");
    }

    Ok(())
}

pub async fn yield_task(id: &str, update_type: SoterUpdateType, resource_id: &str) -> Result<(), Box<dyn std::error::Error>> {
    // Establish connections.
    if let Some(client) = get_client().await {
        // Clone client.
        let mut cloned_client = client.clone();


        // Register task with Soter.
        let request = tonic::Request::new(
            YieldTaskRequest {
                id: id.to_string(),
                update_type: update_type_to_i32(update_type),
                resource_id: resource_id.to_string()
            }
        );
        let response = cloned_client.yield_task(request).await?.into_inner();
        println!("yieldTask = {:?}", response);
    } else {
        panic!("There was a problem!");
    }

    Ok(())
}

pub async fn update_state(id: &str, update_type: SoterUpdateType, resource_id: &str) -> Result<(), Box<dyn std::error::Error>> {
    // Establish connections.
    if let Some(client) = get_client().await {
        // Clone client.
        let mut cloned_client = client.clone();

        // Register task with Soter.
        let request = tonic::Request::new(
            UpdateStateRequest {
                id: id.to_string(),
                update_type: update_type_to_i32(update_type),
                resource_id: resource_id.to_string()
            }
        );
        let response = cloned_client.update_state(request).await?.into_inner();
        println!("updateState = {:?}", response);
    } else {
        panic!("There was a problem!");
    }

    Ok(())
}

pub async fn nondeterministic_boolean_choice() -> Result<bool, Box<dyn std::error::Error>> {
    // Establish connections.
    if let Some(client) = get_client().await {
        // Clone client.
        let mut cloned_client = client.clone();

        // Register task with Soter.
        let request = tonic::Request::new(
            NondeterministicBooleanChoiceRequest { }
        );
        let response = cloned_client.nondeterministic_boolean_choice(request).await?.into_inner();
        println!("nondeterministicBooleanChoice = {:?}", response);
        return Ok(response.result)
    } else {
        panic!("There was a problem!");
    }
}

pub async fn nondeterministic_integer_choice() -> Result<i32, Box<dyn std::error::Error>> {
    // Establish connections.
    if let Some(client) = get_client().await {
        // Clone client.
        let mut cloned_client = client.clone();

        // Register task with Soter.
        let request = tonic::Request::new(
            NondeterministicIntegerChoiceRequest { }
        );
        let response = cloned_client.nondeterministic_integer_choice(request).await?.into_inner();
        println!("nondeterministicIntegerChoice = {:?}", response);
        return Ok(response.result)
    } else {
        panic!("There was a problem!");
    }
}

pub async fn nondeterministic_integer_with_bound_choice(bound: i32) -> Result<i32, Box<dyn std::error::Error>> {
    // Establish connections.
    if let Some(client) = get_client().await {
        // Clone client.
        let mut cloned_client = client.clone();

        // Register task with Soter.
        let request = tonic::Request::new(
            NondeterministicIntegerWithBoundChoiceRequest {
                bound
            }
        );
        let response = cloned_client.nondeterministic_integer_with_bound_choice(request).await?.into_inner();
        println!("nondeterministicIntegerWithBoundChoice = {:?}", response);
        return Ok(response.result)
    } else {
        panic!("There was a problem!");
    }
}

// Adapted from:
// https://users.rust-lang.org/t/any-alternative-to-using-await-with-lazy-static-macro-in-rust/44237/5
//
async fn get_client() -> Option<&'static soter_service::soter_service_client::SoterServiceClient<tonic::transport::channel::Channel>> {
    let client_option = CLIENT.get();
    
    if let Some(_) = client_option {
        return client_option;
    }

    // Try to initialize client.
    let initializing_mutex = CLIENT_INITIALIZED.get_or_init(|| tokio::sync::Mutex::new(false));

    // Block, if another initializer currently has the lock.
    let mut initialized = initializing_mutex.lock().await;

    // If someone just initialized it, then skip.
    if !*initialized {
        // Setup gRPC channel.
        if let Ok(channel) = tonic::transport::Channel::from_static("http://[::1]:50051").connect().await {
            let client = SoterServiceClient::new(channel);

            if let Ok(_) = CLIENT.set(client) {
                *initialized = true;
            }
        }
    }
    drop(initialized);

    // Return the client.
    CLIENT.get()
}
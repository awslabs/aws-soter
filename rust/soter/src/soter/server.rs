mod soter_service {
    tonic::include_proto!("soter");
}

use tokio::sync::oneshot;

use tonic::{transport::Server, Request, Response, Status};

use soter_service::{IsAvailableForRequest,
                    IsAvailableForResponse,
                    HasAssociatedTaskRequest,
                    HasAssociatedTaskResponse,
                    GetAssociatedTaskRequest,
                    GetAssociatedTaskResponse
                    };
use soter_service::soter_client_service_server::{SoterClientService, SoterClientServiceServer};

#[derive(Default)]
pub struct MySoterClientService { }

#[tonic::async_trait]
impl SoterClientService for MySoterClientService {
    // TODO: Fake implementation, this needs to be extracted into module
    // TODO:  and provided with a real implementation of this method.
    async fn is_available_for(
        &self, _request: Request<IsAvailableForRequest>) -> Result<Response<IsAvailableForResponse>, Status> {
            println!("Received IsAvailableForRequest!");

            Ok(Response::new(IsAvailableForResponse {
                result: true
            }))
        }

    async fn has_associated_task(
        &self, _request: Request<HasAssociatedTaskRequest>) -> Result<Response<HasAssociatedTaskResponse>, Status> {
            println!("Received HasAssociatedTaskRequest!");

            Ok(Response::new(HasAssociatedTaskResponse {
                result: false
            }))
        }

    async fn get_associated_task(
        &self, _request: Request<GetAssociatedTaskRequest>) -> Result<Response<GetAssociatedTaskResponse>, Status> {
            println!("Received HasAssociatedTaskRequest!");

            Ok(Response::new(GetAssociatedTaskResponse {
                task_id: String::from("-1")
            }))
        }
}

pub async fn start() -> tokio::sync::oneshot::Receiver<()> {
    let (mut tx1, rx1) = oneshot::channel();

    tokio::spawn(async {
        // Select on the operation and the oneshot's
        // `closed()` notification.
        tokio::select! {
            val = start_sync() => {
                let _ = tx1.send(val);
            }
            _ = tx1.closed() => {
                println!("receiver closed because tx1 closed()");
                // `some_operation()` is canceled, the
                // task completes and `tx1` is dropped.
            }
        }
    });

    // println!("Sleeping for initialization.");
    // tokio::time::delay_for(std::time::Duration::new(5, 0)).await;

    rx1
}

async fn start_sync() -> () {
    // Start the Soter server.
    let addr = "[::1]:60051".parse().unwrap();
    let soter_client_server = MySoterClientService::default();
    println!("Soter client server listening on {}", addr);

    if let Err(_) = Server::builder().add_service(SoterClientServiceServer::new(soter_client_server)).serve(addr).await {
        panic!("Failed to initialize the Soter client server.");
    }

    ()
}

pub async fn stop(rx1: tokio::sync::oneshot::Receiver<()>) -> () {
    println!("Dropping reference to rx1 which should terminate the server.");
    drop(rx1);
}
use tokio::runtime::{Builder, Runtime};

mod soter_service {
    tonic::include_proto!("soter");
}

use soter_service::soter_service_client::SoterServiceClient;

use soter_service::{CreateTaskRequest, 
                    CreateTaskResponse,
                    ExitTaskRequest, 
                    ExitTaskResponse, 
                    AbnormalExitTaskRequest,
                    AbnormalExitTaskResponse,
                    YieldTaskRequest,
                    YieldTaskResponse,
                    BlockTaskRequest,
                    BlockTaskResponse,
                    CancelTaskRequest,
                    CancelTaskResponse,
                    StartTaskRequest,
                    StartTaskResponse,
                    UpdateStateRequest,
                    UpdateStateResponse,
                    NondeterministicBooleanChoiceRequest,
                    NondeterministicBooleanChoiceResponse,
                    NondeterministicIntegerChoiceRequest,
                    NondeterministicIntegerChoiceResponse,
                    NondeterministicIntegerWithBoundChoiceRequest,
                    NondeterministicIntegerWithBoundChoiceResponse};

use crate::soter::SoterUpdateType;
use crate::soter::update_type_to_i32;

use std::sync::atomic::Ordering;

type StdError = Box<dyn std::error::Error + Send + Sync + 'static>;
type Result<T, E = StdError> = ::std::result::Result<T, E>;

// The order of the fields in this struct is important. They must be ordered
// such that when `BlockingClient` is dropped the client is dropped
// before the runtime. Not doing this will result in a deadlock when dropped.
// Rust drops struct fields in declaration order.
pub struct BlockingClient {
    client: SoterServiceClient<tonic::transport::Channel>,
    rt: Runtime,
}

impl BlockingClient {
    pub fn connect<D>(dst: D) -> Result<Self, tonic::transport::Error>
    where
        D: std::convert::TryInto<tonic::transport::Endpoint>,
        D::Error: Into<StdError>,
    {
        let mut rt = Builder::new()
            .basic_scheduler()
            .enable_all()
            .build()
            .unwrap();
        let client = rt.block_on(SoterServiceClient::connect(dst))?;

        Ok(Self { rt, client })
    }

    pub fn create_task(
        &mut self,
        request: impl tonic::IntoRequest<CreateTaskRequest>,
    ) -> Result<tonic::Response<CreateTaskResponse>, tonic::Status> {
        self.rt.block_on(self.client.create_task(request))
    }

    pub fn yield_task(
        &mut self,
        request: impl tonic::IntoRequest<YieldTaskRequest>,
    ) -> Result<tonic::Response<YieldTaskResponse>, tonic::Status> {
        self.rt.block_on(self.client.yield_task(request))
    }

    pub fn block_task(
        &mut self,
        request: impl tonic::IntoRequest<BlockTaskRequest>,
    ) -> Result<tonic::Response<BlockTaskResponse>, tonic::Status> {
        self.rt.block_on(self.client.block_task(request))
    }

    pub fn update_state(
        &mut self,
        request: impl tonic::IntoRequest<UpdateStateRequest>,
    ) -> Result<tonic::Response<UpdateStateResponse>, tonic::Status> {
        self.rt.block_on(self.client.update_state(request))
    }

    pub fn nondeterministic_integer_choice(
        &mut self,
        request: impl tonic::IntoRequest<NondeterministicIntegerChoiceRequest>,
    ) -> Result<tonic::Response<NondeterministicIntegerChoiceResponse>, tonic::Status> {
        self.rt.block_on(self.client.nondeterministic_integer_choice(request))
    }

    pub fn nondeterministic_integer_with_bound_choice(
        &mut self,
        request: impl tonic::IntoRequest<NondeterministicIntegerWithBoundChoiceRequest>,
    ) -> Result<tonic::Response<NondeterministicIntegerWithBoundChoiceResponse>, tonic::Status> {
        self.rt.block_on(self.client.nondeterministic_integer_with_bound_choice(request))
    }

    pub fn nondeterministic_boolean_choice(
        &mut self,
        request: impl tonic::IntoRequest<NondeterministicBooleanChoiceRequest>,
    ) -> Result<tonic::Response<NondeterministicBooleanChoiceResponse>, tonic::Status> {
        self.rt.block_on(self.client.nondeterministic_boolean_choice(request))
    }

    pub fn exit_task(
        &mut self,
        request: impl tonic::IntoRequest<ExitTaskRequest>,
    ) -> Result<tonic::Response<ExitTaskResponse>, tonic::Status> {
        self.rt.block_on(self.client.exit_task(request))
    }

    pub fn abnormal_exit_task(
        &mut self,
        request: impl tonic::IntoRequest<AbnormalExitTaskRequest>,
    ) -> Result<tonic::Response<AbnormalExitTaskResponse>, tonic::Status> {
        self.rt.block_on(self.client.abnormal_exit_task(request))
    }

    pub fn cancel_task(
        &mut self,
        request: impl tonic::IntoRequest<CancelTaskRequest>,
    ) -> Result<tonic::Response<CancelTaskResponse>, tonic::Status> {
        self.rt.block_on(self.client.cancel_task(request))
    }

    pub fn start_task(
        &mut self,
        request: impl tonic::IntoRequest<StartTaskRequest>,
    ) -> Result<tonic::Response<StartTaskResponse>, tonic::Status> {
        self.rt.block_on(self.client.start_task(request))
    }
}

pub fn create_task() -> Result<String, Box<dyn std::error::Error>> {
    let task_id = crate::soter::NEXT_TASK_ID.fetch_add(1, Ordering::SeqCst);
    let id : String = task_id.to_string();
    let return_id = id.clone();

    let mut client = get_client()?;

    let request = tonic::Request::new(CreateTaskRequest {
        id: id.to_string()
    });

    let response = client.create_task(request);

    println!("createTask = {:?}", response);

    Ok(return_id)
}

pub fn cancel_task(id: &str) -> Result<(), Box<dyn std::error::Error>> {
    let mut client = get_client()?;

    let request = tonic::Request::new(CancelTaskRequest {
        id: id.to_string()
    });

    let response = client.cancel_task(request);

    println!("cancelTask = {:?}", response);

    Ok(())
}

pub fn exit_task(id: &str) -> Result<(), Box<dyn std::error::Error>> {
    let mut client = get_client()?;

    let request = tonic::Request::new(ExitTaskRequest {
        id: id.to_string()
    });

    let response = client.exit_task(request);

    println!("exitTask = {:?}", response);

    Ok(())
}

pub fn block_task(id: &str) -> Result<(), Box<dyn std::error::Error>> {
    let mut client = get_client()?;

    let request = tonic::Request::new(BlockTaskRequest {
        id: id.to_string()
    });

    let response = client.block_task(request);

    println!("blockTask = {:?}", response);

    Ok(())
}

pub fn start_task(id: &str) -> Result<(), Box<dyn std::error::Error>> {
    let mut client = get_client()?;

    let request = tonic::Request::new(StartTaskRequest {
        id: id.to_string()
    });

    let response = client.start_task(request);

    println!("startTask = {:?}", response);

    Ok(())
}

pub fn abnormal_exit_task(id: &str) -> Result<(), Box<dyn std::error::Error>> {
    let mut client = get_client()?;

    let request = tonic::Request::new(AbnormalExitTaskRequest {
        id: id.to_string()
    });

    let response = client.abnormal_exit_task(request);

    println!("abnormalExitTask = {:?}", response);

    Ok(())
}

pub fn yield_task(id: &str, update_type: SoterUpdateType, resource_id: &str) -> Result<(), Box<dyn std::error::Error>> {
    let mut client = get_client()?;

    let request = tonic::Request::new(YieldTaskRequest {
        id: id.to_string(),
        update_type: update_type_to_i32(update_type),
        resource_id: resource_id.to_string()
    });

    let response = client.yield_task(request);

    println!("yieldTask = {:?}", response);

    Ok(())
}

pub fn update_state(id: &str, update_type: SoterUpdateType, resource_id: &str) -> Result<(), Box<dyn std::error::Error>> {
    let mut client = get_client()?;

    let request = tonic::Request::new(UpdateStateRequest {
        id: id.to_string(),
        update_type: update_type_to_i32(update_type),
        resource_id: resource_id.to_string()
    });

    let response = client.update_state(request);

    println!("updateState = {:?}", response);

    Ok(())
}

pub fn nondeterministic_integer_choice() -> Result<i32, Box<dyn std::error::Error>> {
    let mut client = get_client()?;

    let request = tonic::Request::new(NondeterministicIntegerChoiceRequest { });

    let response = client.nondeterministic_integer_choice(request);

    println!("nondeterministicIntegerChoice = {:?}", response);

    let result = response.unwrap().into_inner().result;

    Ok(result)
}

pub fn nondeterministic_integer_with_bound_choice(bound: i32) -> Result<i32, Box<dyn std::error::Error>> {
    let mut client = get_client()?;

    let request = tonic::Request::new(NondeterministicIntegerWithBoundChoiceRequest { 
        bound: bound
    });

    let response = client.nondeterministic_integer_with_bound_choice(request);

    println!("nondeterministicIntegerWithBoundChoice = {:?}", response);

    let result = response.unwrap().into_inner().result;

    Ok(result)
}

pub fn nondeterministic_boolean_choice() -> Result<bool, Box<dyn std::error::Error>> {
    let mut client = get_client()?;

    let request = tonic::Request::new(NondeterministicBooleanChoiceRequest { });

    let response = client.nondeterministic_boolean_choice(request);

    println!("nondeterministicBooleanChoice = {:?}", response);

    let result = match response {
        Ok(result) => result.into_inner().result,
        Err(e) => {
            panic!("Something bad happened: {}", e);
        }
    };

    Ok(result)
}

// TODO: does this need to be synchronized?
fn get_client() -> Result<BlockingClient, Box<dyn std::error::Error>> {
    let client = BlockingClient::connect("http://127.0.0.1:50051")?;
    Ok(client)
}
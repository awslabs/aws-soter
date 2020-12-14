pub mod server;
pub mod client;
pub mod sync_client;
pub mod thread;
pub mod tokio;
pub mod rand;

use std::error::Error;
use std::fmt;
use std::convert::From;

#[derive(Debug)]
pub struct SoterError;

impl fmt::Display for SoterError {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        write!(f, "SoterError is here!")
    }
}

impl Error for SoterError { }

impl From<std::boxed::Box<dyn std::error::Error>> for SoterError {
    fn from(_ : std::boxed::Box<dyn std::error::Error>) -> Self { 
        SoterError
    }
}

use std::sync::atomic::AtomicUsize;

static NEXT_TASK_ID: AtomicUsize = AtomicUsize::new(1);

pub enum SoterUpdateType {
    NoOp,
    ResourceNeeded,
    ResourceNoLongerNeeded
}

pub fn update_type_to_i32(update_type: SoterUpdateType) -> i32 {
    // Update type conversion.  
    // TODO: This could probably be done easier through the gRPC library, but I'm not sure how yet.
    match update_type {
        SoterUpdateType::NoOp => 0,
        SoterUpdateType::ResourceNeeded => 1,
        SoterUpdateType::ResourceNoLongerNeeded => 2,
    }
}

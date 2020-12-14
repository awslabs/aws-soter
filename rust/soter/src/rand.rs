// Main program.

pub mod soter;

#[tokio::main]
async fn main() -> Result<(), Box<dyn std::error::Error>> {
    let soter_server = soter::server::start().await;

    let secret_number : i32 = soter::rand::thread_rng().gen_range(1, 50);

    if secret_number == 23 {
        std::process::exit(1);
    }
   
    soter::server::stop(soter_server).await;

    Ok(())
}
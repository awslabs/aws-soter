// Main program.

pub mod soter;

#[tokio::main]
async fn main() -> Result<(), Box<dyn std::error::Error>> {
    let soter_server = soter::server::start().await;

    // Body of the main program.
    println!("Hello, I have started and now I'm about to exit.");

    soter::server::stop(soter_server).await;

    Ok(())
}
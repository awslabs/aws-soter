use tokio;
use tokio::runtime::Runtime;

pub mod soter;

#[tokio::main]
async fn main() -> () {
    do_thing();
}

fn do_thing() -> () {

    let handler = std::thread::spawn(|| {
        let x = String::from("1");
        Runtime::new().unwrap().block_on(soter::client::cancel_task(&x)).unwrap()
    });
    
    handler.join().unwrap();

}
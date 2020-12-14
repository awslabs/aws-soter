pub fn thread_rng() -> crate::soter::rand::ThreadRng {
    ThreadRng { }
}

pub struct ThreadRng {

}

impl ThreadRng {
    pub fn gen_range(self, lower: i32, upper: i32) -> i32 {
        let zeroed_upper : i32 = upper - lower;

        let result : i32 = std::thread::spawn(move || {
            crate::soter::sync_client::nondeterministic_integer_with_bound_choice(zeroed_upper).unwrap()
        }).join().unwrap();

        let result = result + lower;

        result
    }
}

pub fn random() -> bool {
    let result : bool = std::thread::spawn(move || {
        crate::soter::sync_client::nondeterministic_boolean_choice().unwrap()
    }).join().unwrap();

    result
}

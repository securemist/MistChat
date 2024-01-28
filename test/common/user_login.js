const users = []

var baseNum = 10000;
for (let i = 0; i < 10; i++) {
    users[i] = {
        "username": `${baseNum + i}`,
        "password": `${baseNum + i}`,
        "name": `u_${baseNum + i}`
    }
}

function httpUserRegister() {

}


console.log(users)
// for (let user of users) {
//     console.log(user)
// }
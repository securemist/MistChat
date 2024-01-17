import { io } from 'socket.io-client';

const socket = io("ws://127.0.0.1:8201?token=123");
// console.log(socket)
// socket.connect()
// socket.send(123)
// socket.on("connect",(socket) => {
//     console.log(123)
// })

socket.emit("join",{name:1})
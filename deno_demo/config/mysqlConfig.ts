import { Client } from "../deps.ts";

const client = await new Client();

client.connect({
    username: "root",
    password: "1234QWERasdf.",
    hostname: "192.168.1.241",
    db: "zwkhd",
    poolSize: 3, // connection limit

});

export default client;

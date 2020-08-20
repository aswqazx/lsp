import { Client } from "../deps.ts";

export const mysqlClient = new Client();

mysqlClient.connect({
    username: "root",
    password: "1234QWERasdf.",
    hostname: "192.168.1.241",
    db: "zwkhd",
    poolSize: 3, // connection limit

});

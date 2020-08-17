import { Client } from "https://deno.land/x/mysql/mod.ts";

const client = await new Client();

client.connect({
    username: "aswqazx",
    password: "aswqazx",
    hostname: "127.0.0.1",
    db: "aswqazx",
    poolSize: 3, // connection limit

});

export default client;

import { Database } from "../deps.ts";
import { SysUserModel } from "../entity/sysUserModel.ts";

export const denondb = new Database("mysql", {
    database: "zwkhd",
    host: "192.168.1.241",
    username: "root",
    password: "1234QWERasdf.",
    port: 3306,
});

denondb.link([SysUserModel]);

import { Database } from "../deps.ts";
import SysUserModel from "../entity/sysUser.ts";

export const db = new Database({ dialect: "mysql", debug: true }, {
        database: "zwkhd",
        host: "192.168.1.241",
        username: "root",
        password: "1234QWERasdf.",
        port: 3306,
    });

db.link([SysUserModel]);


import { log, makeJwt, setExpiration, Jose, Payload } from "../deps.ts";
import { SysUserModel } from "../entity/sysUserModel.ts";
import { mysqlClient } from "../config/mysqlConfig.ts";
import { ResultInfo } from "../entity/resultInfo.ts";

export class SysUserController {
    static async login(ctx: any) {
        log.info("sysUserController[login] =================>");
        if (ctx.request.hasBody) {
            const body = await ctx.request.body();
            const value = await body.value;
            log.info(`param username is: ${value.username}`);
            const result = await SysUserModel.where('user_name', value.username).get();
            if (result) {
                if (result[0].password === value.password) {
                    const payload: Payload = {
                        iss: "aswqazx",
                        iat: new Date().getTime(),
                        exp: setExpiration(Number(Deno.env.get("JWT_EXPIRATION_TIME"))),
                        // 自定义
                        username: value.username
                    };
                    const header: Jose = {
                        alg: "HS256",
                        typ: Deno.env.get("JWT_TYPE")
                    };
                    const key = Deno.env.get("JWT_SECRET_KEY") || "";
                    const token = await makeJwt({ header, payload, key })
                    const data = {
                        token: token
                    }
                    ctx.response.body = ResultInfo.success("登录成功", data, null);
                } else {
                    ctx.response.body = ResultInfo.failure("登录失败，密码不正确");
                }
            } else {
                ctx.response.body = ResultInfo.failure("登录失败，用户不存在");
            }
        } else {
            ctx.response.body = ResultInfo.failure("查询失败，参数无效");
        }
    }
    static async getList(ctx: any) {
        log.info("sysUserController[getList] =================>");
        if (ctx.request.hasBody) {
            const body = await ctx.request.body();
            const value = await body.value;
            log.info(`param name is: ${value.name}`);
            const limit = value.limit;
            const page = (value.page - 1) * limit;
            const total = await SysUserModel.count();
            const data = await SysUserModel.offset(page).limit(limit).orderBy('asid', 'desc').all();
            ctx.response.body = ResultInfo.success("查询成功", data, total);
        } else {
            ctx.response.body = ResultInfo.failure("查询失败，参数无效");
        }
        return;
    }
    static async getSysUser(ctx: any) {
        log.info("sysUserController[getSysUser] =================>");
        if (ctx.request.hasBody) {
            const body = await ctx.request.body();
            const value = await body.value;
            log.info(`param id is: ${value.id}`);
            const data = await mysqlClient.query(`SELECT * FROM sys_user WHERE id = ?`, [value.id])
            ctx.response.body = ResultInfo.success("查询成功", data, null);
        } else {
            ctx.response.body = ResultInfo.failure("查询失败，参数无效");
        }
    }
}

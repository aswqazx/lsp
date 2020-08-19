import { log } from "../deps.ts";

import SysUserModel from "../entity/sysUser.ts";
import client from "../config/mysqlConfig.ts";
import resultInfo from "../entity/resultInfo.ts";

class Controller {
    static async getList(ctx: any) {
        if (ctx.request.hasBody) {
            const body = await ctx.request.body();
            const value = await body.value;
            log.info(value.name);
            const limit = value.limit
            const page = (value.page - 1) * limit
            try {
                const total = await SysUserModel.count();
                const data = await SysUserModel.offset(page).limit(limit).orderBy('asid', 'desc').all();
                ctx.response.body = resultInfo.success("this is sysUser", data, total);
            } catch (e) {
                log.error(e.toString())
                ctx.response.body = resultInfo.failure(e.toString())
            }
        } else {
            ctx.response.body = resultInfo.failure("查询失败，参数无效");
        }
    }
    static async getSysUser(ctx: any) {
        if (ctx.request.hasBody) {
            const body = await ctx.request.body();
            const value = await body.value;
            log.info(value.id);
            const data = await client.query(`SELECT * FROM sys_user WHERE id = ?`, [value.id])
            ctx.response.body = resultInfo.success("查询成功", data, null);
        } else {
            ctx.response.body = resultInfo.failure("查询失败，参数无效");
        }
    }
}

export default Controller;

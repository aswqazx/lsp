import client from "../config/mysqlConfig.ts";

export default {
    getList: async (ctx: any) => {
        if (ctx.request.hasBody) {
            const body = await ctx.request.body();
            const value = await body.value;
            console.info(value.name);
            console.info(value.password);
            ctx.response.body = {
                code: 1,
                message: 'this is sysUser',
                data: null
            };
        } else {
            ctx.response.body = {
                code: 2,
                message: '查询失败，参数无效',
                data: null
            };
        }
    },
    getSysUser: async (ctx: any) => {
        if (ctx.request.hasBody) {
            const body = await ctx.request.body();
            const value = await body.value;
            console.info(value.id);
            const data = await client.query(`SELECT * FROM sys_user WHERE id = ?`, [value.id])
            ctx.response.body = {
                code: 1,
                message: '查询成功',
                data: data
            };
        } else {
            ctx.response.body = {
                code: 2,
                message: '查询失败，参数无效',
                data: null
            };
        }
    }
};

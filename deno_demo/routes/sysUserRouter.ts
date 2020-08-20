import { Router } from "../deps.ts";
import { SysUserController } from "../controller/sysUserController.ts";
import { jwtAuth } from "../middlewares/jwt.ts";

export const sysUserRouter = new Router();
sysUserRouter.prefix('/users')

sysUserRouter.post("/getList", jwtAuth, SysUserController.getList);
sysUserRouter.post("/getSysUser", SysUserController.getSysUser);
sysUserRouter.post("/login", SysUserController.login);

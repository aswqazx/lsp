import { Router } from "https://deno.land/x/oak/mod.ts";
// controller
import sysUserController from "../controller/sysUser.ts";

const router = new Router();
router.prefix('/users')
router.post("/getList", sysUserController.getList)
    .post("/getSysUser", sysUserController.getSysUser);

export default router;

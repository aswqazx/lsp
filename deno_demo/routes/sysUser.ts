import { Router } from "../deps.ts";
// controller
import sysUserController from "../controller/sysUser.ts";

const router = new Router();
router.prefix('/users')

router.post("/getList", sysUserController.getList);
router.post("/getSysUser", sysUserController.getSysUser);

export default router;

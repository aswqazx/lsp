import { Router } from "../deps.ts";
// controller
import indexController from "../controller/index.ts";

const router = new Router();

router.get("/", indexController.getIndex);

export default router;

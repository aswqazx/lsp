import { Router } from "../deps.ts";
import { IndexController } from "../controller/indexController.ts";

export const indexRouter = new Router();

indexRouter.get("/", IndexController.getIndex);

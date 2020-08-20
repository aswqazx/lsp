import { log } from "../deps.ts";
import { ResultInfo } from "../entity/resultInfo.ts";

export class IndexController {
    static async getIndex(
        { response, request }: { response: any, request: any },
        next: Function) {
        log.info("indexController[getIndex] =================>");
        response.status = 200;
        response.body = ResultInfo.success("this is a index", null, null);
    }
}

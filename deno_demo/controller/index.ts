import { log } from "../deps.ts";

class Controller {
    static async getIndex(
        { response, request }: { response: any, request: any },
        next: Function) {
        log.info("aaaaa")
        response.status = 200;
        response.body = {
            code: 1,
            message: 'this is index',
            data: null
        };
    }
}

export default Controller;

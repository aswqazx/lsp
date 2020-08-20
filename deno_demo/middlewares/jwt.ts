import { log, validateJwt } from "../deps.ts";
import { ResultInfo } from "../entity/resultInfo.ts";

export async function jwtAuth(ctx: any, next: Function) {
    // Get the token from the request
    const token = ctx.request.headers.get("Authorization")?.replace("Bearer ", "");
    // reject request if token was not provide
    if (!token) {
        ctx.response.body = ResultInfo.failure("没有token");
        return;
    }
    const key = Deno.env.get("JWT_SECRET_KEY") || "";
    // check the validity of the token
    const validatedJwt = await validateJwt({ jwt: token, key, algorithm: ["HS256"] });
    if (!validatedJwt.isValid) {
        ctx.response.body = ResultInfo.failure("token验证失败");
        return;
    }
    log.info(validatedJwt)
    // JWT is correct, so continue and call the private route
    await next();
}

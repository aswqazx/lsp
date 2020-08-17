import { Application } from "https://deno.land/x/oak/mod.ts";
import sysUserRouter from "./routes/sysUser.ts";
import indexRouter from "./routes/index.ts";

const app = new Application();
const port: number = 3000;

app.use(indexRouter.routes(), indexRouter.allowedMethods());
app.use(sysUserRouter.routes(), sysUserRouter.allowedMethods());

console.log('running on port ', port);
await app.listen({port});

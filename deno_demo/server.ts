import { Application, v4, log, load } from "./deps.ts";

import sysUserRouter from "./routes/sysUser.ts";
import indexRouter from "./routes/index.ts";
import { db } from "./config/denodbConfig.ts";

// uuid
const myUUID = v4.generate();
log.info(myUUID);

// denv
await load(".env");

const app = new Application();
const port: number = Number(Deno.env.get("PORT"));

app.use(indexRouter.routes(), indexRouter.allowedMethods());
app.use(sysUserRouter.routes(), sysUserRouter.allowedMethods());

await db.sync();

// console.log('running on port ', port);
log.info(`Server running on port: ${port}`)
await app.listen({port});

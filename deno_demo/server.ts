import { Application, v4, log, c, load } from "./deps.ts";
import { indexRouter } from "./routes/indexRouter.ts";
import { sysUserRouter } from "./routes/sysUserRouter.ts";
import { denondb } from "./config/denodbConfig.ts";

// log
await log.setup({
    handlers: {
        console: new log.handlers.ConsoleHandler("DEBUG", {
            formatter: "{datetime} [{levelName}] {msg}"
        }),
    },
    loggers: {
        default: {
            level: "DEBUG",
            handlers: ["console"],
        },
    },
});

// uuid
const myUUID = v4.generate();
log.info(`uuid is: ${c.red(myUUID)}`)
log.debug("this is debug");
log.info("this is info");
log.warning("this is warning");
log.error("this is error");
log.critical("this is critical");

// denv
await load(".env");

const app = new Application();
const port = Deno.env.get("SERVER_PORT") || "";

app.use(indexRouter.routes(), indexRouter.allowedMethods());
app.use(sysUserRouter.routes(), sysUserRouter.allowedMethods());

// denodb
await denondb.sync();

// console.log('running on port ', port);
log.info(`Server running on port: ${c.yellow(port)}`)
await app.listen({port: Number(port)});

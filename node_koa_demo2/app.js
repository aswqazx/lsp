const Koa = require('koa')
const app = new Koa()
const views = require('koa-views')
const json = require('koa-json')
const onerror = require('koa-onerror')
const bodyparser = require('koa-bodyparser')
const logger = require('koa-logger')
// jwt
// 请求头要添加 authorization： Bearer eyJxxxx
const jwtKoa = require('koa-jwt')
const { jwtConfig } = require('./config/baseConfig')
// cors跨域
const corsKoa = require('koa2-cors')

const index = require('./routes/index')
const users = require('./routes/users')

// error handler
onerror(app)

// middlewares
app.use(bodyparser({
  enableTypes:['json', 'form', 'text']
}))
app.use(json())
app.use(logger())
app.use(require('koa-static')(__dirname + '/public'))

// jwt
app.use(async(ctx, next)=>{
  return next().catch((err) => {
    if (401 === err.status) {
      ctx.status = 401
      ctx.body = {
        status:401,
        msg:'登录过期，请重新登录'
      }
    } else {
      throw err
    }
  })
})
app.use(jwtKoa({secret: jwtConfig.secret}).unless({
    //数组中的路径不需要通过jwt验证
    path: [
      /\/users\/login/
    ]
}))

// cors跨域
app.use(corsKoa())

app.use(views(__dirname + '/views', {
  extension: 'ejs'
}))

// logger
app.use(async (ctx, next) => {
  const start = new Date()
  await next()
  const ms = new Date() - start
  console.log(`${ctx.method} ${ctx.url} - ${ms}ms`)
})

// routes
app.use(index.routes(), index.allowedMethods())
app.use(users.routes(), users.allowedMethods())

// error-handling
app.on('error', (err, ctx) => {
  console.error('server error', err, ctx)
});

module.exports = app

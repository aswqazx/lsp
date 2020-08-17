const router = require('koa-router')()
const userControl = require('../controller/sysUser') //引入逻辑

router.prefix('/users')

router.post('/getById', userControl.findSysUserById)

module.exports = router

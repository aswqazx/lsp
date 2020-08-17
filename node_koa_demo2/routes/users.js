const router = require('koa-router')()
const userControl = require('../controller/sysUser') //引入逻辑

router.prefix('/users')

// 登录
router.post('/login', userControl.login)

// 根据id获取单个用户信息
router.post('/getById', userControl.getById)

// 获取用户信息列表
router.post('/getList', userControl.getSysUserList)

// 添加用户信息
router.post('/add', userControl.addSysUser)

// 更新用户信息
router.post('/update', userControl.updateSysUser)

// 删除用户信息
router.post('/delete', userControl.deleteSysUser)

module.exports = router

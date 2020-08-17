const baseConfig= {
  databaseConfig: {
    username: 'root',
    password: '1234QWERasdf.',
    databaseName: 'zwkhd',
    host: '192.168.1.241',
    port: 3306
  },
  jwtConfig: {
    secret: 'jwt_secret',
    expiresIn: '1h'
  }
}

module.exports = baseConfig

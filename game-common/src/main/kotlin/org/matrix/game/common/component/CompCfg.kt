package org.matrix.game.common.component

abstract class CompCfg : AbstractComponent() {
    abstract var actorSystemName: String
    abstract var akkaHost: String
    abstract var akkaPort: Int
    abstract var seedNodes: MutableList<String>
}

interface ICfg4Db {
    var host: String
    var dbName: String
    var username: String
    var password: String
}

// 带有数据库连接信息
abstract class CompCfg4Db : CompCfg(), ICfg4Db
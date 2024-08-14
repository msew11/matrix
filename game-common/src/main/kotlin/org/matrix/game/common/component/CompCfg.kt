package org.matrix.game.common.component

abstract class CompCfg : AbstractComponent() {
    abstract var actorSystemName: String
    abstract var akkaHost: String
    abstract var akkaPort: Int
    abstract var seedNodes: MutableList<String>
}
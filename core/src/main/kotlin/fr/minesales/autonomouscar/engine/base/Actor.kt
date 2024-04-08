package fr.minesales.autonomouscar.engine.base

import com.badlogic.gdx.graphics.g3d.Model
import fr.minesales.autonomouscar.engine.RigidBodyInfo

data class Actor(val name: String, val model: Model? = null, val rigidbodyInfo: RigidBodyInfo? = null)

package braynstorm.hellven.game.observation

/**
 * TODO Add class description
 * Created by Braynstorm on 4.4.2017 г..
 */
interface Observable<T>{
	operator fun plusAssign(observer:T)
	operator fun minusAssign(observer:T)
}

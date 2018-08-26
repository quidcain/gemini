import dashboardSagas from '../redux/sagas/dashboard'
import loginSagas from '../redux/sagas/login'

export default function* rootSaga() {
  yield [
    ...dashboardSagas,
    ...loginSagas,
  ]
}

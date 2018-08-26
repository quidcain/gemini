import Loadable from 'react-loadable'
import Loading from './loading'

const loadable = loader =>
  Loadable({
    loader: () => loader,
    loading: Loading,
    delay: 300,
    timeout: 10000
  });

export default loadable;

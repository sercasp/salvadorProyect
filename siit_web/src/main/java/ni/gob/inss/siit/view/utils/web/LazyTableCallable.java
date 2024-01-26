package ni.gob.inss.siit.view.utils.web;

@FunctionalInterface
public interface LazyTableCallable<V> {

    V call(Integer index, Integer pageSize) throws Exception;

}
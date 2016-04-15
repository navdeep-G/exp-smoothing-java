package transform;

import water.fvec.Frame;
import water.fvec.Vec;

/**
 * A base class for all simple transformations on a Frame.
 * Created by nkalonia1 on 3/29/16.
 */
public abstract class SimpleTransformFrame implements TransformFrame {

    @Override
    public Frame transform(Frame f) {
        for (Vec v : f.vecs()) {
            transform(v);
        }
        return f;
    }

    public Vec transform(Vec v) {
        for (long r = 0; r < v.length(); ++r) {
            v.set(r, transform(v.at(r)));
        }
        return v;
    }

    public abstract double transform(double d);
}

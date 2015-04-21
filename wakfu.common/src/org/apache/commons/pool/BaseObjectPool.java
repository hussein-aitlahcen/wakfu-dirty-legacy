package org.apache.commons.pool;

public abstract class BaseObjectPool implements ObjectPool
{
    private volatile boolean closed;
    
    public BaseObjectPool() {
        super();
        this.closed = false;
    }
    
    @Override
	public abstract Object borrowObject() throws Exception;
    
    @Override
	public abstract void returnObject(final Object p0) throws Exception;
    
    @Override
	public abstract void invalidateObject(final Object p0) throws Exception;
    
    @Override
	public int getNumIdle() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
    
    @Override
	public int getNumActive() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
    
    @Override
	public void clear() throws Exception, UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
    
    @Override
	public void addObject() throws Exception, UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
    
    @Override
	public void close() throws Exception {
        this.assertOpen();
        this.closed = true;
    }
    
    @Override
	public void setFactory(final PoolableObjectFactory factory) throws IllegalStateException, UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
    
    protected final boolean isClosed() {
        return this.closed;
    }
    
    protected final void assertOpen() throws IllegalStateException {
        if (this.isClosed()) {
            throw new IllegalStateException("Pool not open");
        }
    }
}

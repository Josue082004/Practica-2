package com.unl.music.base.controller.data_struct.queque;

import com.unl.music.base.controller.data_struct.list.LinkedList;

final class QueueImplementation<E> extends LinkedList<E> {
    private Integer top;
    
    public QueueImplementation(Integer top) {
        super();
        this.top = top;
    }
    
    protected boolean IsFullQueue() {
        return this.top >= getLength();
    }

    protected void Queue(E info) throws Exception {
        if (!IsFullQueue()) {
            this.add(info, 0);
        } else {
            throw new ArrayIndexOutOfBoundsException("Queque is full/ ta lleno pa");
        }
    }

    protected E dequeue()throws Exception {
        return deleteFirst();
    }

    public Integer getTop() {
        return this.top;
    }
}
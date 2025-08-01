package com.dnd.spaced.global.repository;

import com.querydsl.core.types.NullExpression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;

@SuppressWarnings({"rawtypes", "unchecked"})
public class OrderByNull extends OrderSpecifier {

    public static OrderByNull DEFAULT = new OrderByNull();

    private OrderByNull() {
        super(Order.ASC, NullExpression.DEFAULT, NullHandling.Default);
    }
}

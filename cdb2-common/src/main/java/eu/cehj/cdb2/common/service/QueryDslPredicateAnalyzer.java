package eu.cehj.cdb2.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.querydsl.core.types.Constant;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.FactoryExpression;
import com.querydsl.core.types.Operation;
import com.querydsl.core.types.ParamExpression;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.SubQueryExpression;
import com.querydsl.core.types.TemplateExpression;
import com.querydsl.core.types.Visitor;

@Service
public class QueryDslPredicateAnalyzer implements Visitor<Constant<?>, Void>{

    public static final QueryDslPredicateAnalyzer DEFAULT = new QueryDslPredicateAnalyzer();


    @Override
    public Constant<?> visit(final Constant<?> expr, final Void context)
    {
        return expr;
    }


    @Override
    public Constant<?> visit(final FactoryExpression<?> expr, final Void context)
    {
        return this.visit(expr.getArgs());
    }


    @Override
    public Constant<?> visit(final Operation<?> expr, final Void context)
    {
        return this.visit(expr.getArgs());
    }


    @Override
    public Constant<?> visit(final ParamExpression<?> expr, final Void context)
    {
        return null;
    }


    @Override
    public Constant<?> visit(final Path<?> expr, final Void context)
    {
        return null;
    }


    @Override
    public Constant<?> visit(final SubQueryExpression<?> expr, final Void context)
    {
        return null;
    }


    @Override
    public Constant<?> visit(final TemplateExpression<?> expr, final Void context)
    {
        return this.visit(expr.getArgs());
    }


    private Constant<?> visit(final List<?> exprs)
    {
        for (final Object e : exprs)
        {
            if (e instanceof Expression)
            {
                final Constant<?> constant = ((Expression<?>) e).accept(this, null);
                if (constant != null)
                {
                    return constant;
                }
            }
        }

        return null;
    }

}

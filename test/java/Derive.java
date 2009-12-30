// Decompiled by Jad v1.5.8d. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Derive.java


import java.util.Vector;

// Referenced classes of package lundin.SymbolicMath:
//            GUIDerive, notValidSyntaxException

public class Derive
{

    public Derive()
    {
        maxoplength = setMaxOpLength();
        VARIABLES = "";
        storedvars = "";
        defaultvar = "x";
        tempstorage = new Vector();
    }

    int setMaxOpLength()
    {
        boolean flag = false;
        int j = 0;
        for(int i = 0; i < allowedops.length; i++)
            if(allowedops[i].length() > j)
                j = allowedops[i].length();

        return j;
    }

    String car(String s)
    {
        s.length();
        int i = 0;
        int j = 2;
        int k = 0;
        String s1 = "";
        if(s.charAt(2) == '(')
        {
            for(; j < s.length(); j++)
            {
                if(s.charAt(j) == '(')
                    k++;
                else
                if(s.charAt(j) == ')')
                    k--;
                if(k != 0)
                    continue;
                i = j;
                break;
            }

            String s2 = s.substring(2, i + 1);
            return s2;
        } else
        {
            String s3 = s.substring(2, s.indexOf(" ", 2));
            return s3;
        }
    }

    String cdr(String s)
    {
        String s1 = "";
        String s2 = "";
        s1 = s1 + car(s);
        s2 = s.substring(s1.length() + 2, s.length());
        return "(" + s2;
    }

    String arg1(String s)
    {
        return car(cdr(s));
    }

    String arg2(String s)
    {
        return car(cdr(cdr(s)));
    }

    void Syntax(String s)
        throws Exception
    {
        Object obj = null;
        Object obj1 = null;
        int i = 0;
        if(!MatchParant(s))
            throw new Exception("Non matching brackets");
        while(i < s.length()) 
        {
            String s1;
            try
            {
                if((s1 = getOp(s, i)) != null)
                {
                    String s2 = getOp(s, s1.length() + i);
                    if(isTwoArgOp(s2) && !s2.equals("+") && !s2.equals("-"))
                        throw new Exception("Syntax error near -> " + s.substring(i, s.length()));
                    if(!isTwoArgOp(s1) && BackTrack(s.substring(0, i)) == null && isConstant(s.charAt(i - 1)))
                        throw new Exception("Missing operator before -> " + s.substring(i, s.length()));
                } else
                if(!isAlpha(s.charAt(i)) && !isConstant(s.charAt(i)) && !isAllowedSym(s.charAt(i)))
                    throw new Exception("Syntax error near -> " + s.substring(i, s.length()));
            }
            catch(StringIndexOutOfBoundsException _ex) { }
            i++;
            Object obj2 = s1 = null;
        }
    }

    boolean MatchParant(String s)
    {
        int i = 0;
        boolean flag = false;
        for(int j = 0; j < s.length(); j++)
            if(s.charAt(j) == '(')
                i++;
            else
            if(s.charAt(j) == ')')
                i--;

        return i == 0;
    }

    String list(String s, String s1, String s2)
    {
        return "( " + s + " " + s1 + " " + s2 + " )";
    }

    String list(String s, String s1)
    {
        return "( " + s + " " + s1 + " )";
    }

    boolean isVariable(String s)
    {
        boolean flag = false;
        if(isAllNumbers(s))
            return false;
        for(int i = 0; i < s.length(); i++)
            if(getOp(s, i) != null || isAllowedSym(s.charAt(i)))
                return false;

        return true;
    }

    public boolean isSupported(String s)
    {
        String s1 = "";
        if(s.indexOf("(") != -1)
            s1 = s.substring(0, s.indexOf("("));
        else
            s1 = s;
        return isOperator(s1);
    }

    boolean isAlpha(String s)
    {
        if(s == null)
            return false;
        if(s.length() > 1)
            return false;
        char c = s.charAt(0);
        return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z';
    }

    boolean isAlpha(char c)
    {
        return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z';
    }

    boolean isAllowedSym(char c)
    {
        return c == '(' || c == ')' || c == '.';
    }

    boolean isConstant(char c)
    {
        return isConstant(String.valueOf(c));
    }

    boolean isConstant(String s)
    {
        try
        {
            if(Double.isNaN(Double.valueOf(s).doubleValue()))
                return false;
        }
        catch(Exception _ex)
        {
            return false;
        }
        return true;
    }

    boolean isAllNumbers(String s)
    {
        int i = 0;
        boolean flag = false;
        char c = s.charAt(0);
        if(c == '-' || c == '+')
            i = 1;
        for(; i < s.length(); i++)
        {
            char c1 = s.charAt(i);
            if(!isConstant(c1) && (c1 != '.' || flag))
                return false;
            if(c1 == '.')
                flag = true;
        }

        return true;
    }

    boolean isSameVariable(String s, String s1)
    {
        return isVariable(s) && isVariable(s1) && s.equalsIgnoreCase(s1);
    }

    boolean isOperator(String s)
    {
        boolean flag = false;
        for(int i = 0; i < allowedops.length; i++)
            if(allowedops[i].equalsIgnoreCase(s))
                return true;

        return false;
    }

    boolean isTwoArgOp(String s)
    {
        boolean flag = false;
        for(int i = 0; i < twoargops.length; i++)
            if(twoargops[i].equalsIgnoreCase(s))
                return true;

        return false;
    }

    boolean isOne(String s)
    {
        return Double.valueOf(s).doubleValue() == 1.0D;
    }

    boolean isZero(String s)
    {
        return Double.valueOf(s).doubleValue() == 0.0D;
    }

    boolean isInteger(double d)
    {
        return d - (double)(int)d == 0.0D;
    }

    boolean isEven(int i)
    {
        return isInteger(i / 2);
    }

    boolean isEven(double d)
    {
        return isInteger(d / 2D);
    }

    boolean isEven(float f)
    {
        return isInteger(f / 2.0F);
    }

    boolean isSum(String s)
    {
        return car(s).equals("+");
    }

    boolean isSubtraction(String s)
    {
        return car(s).equals("-");
    }

    boolean isProduct(String s)
    {
        return car(s).equals("*");
    }

    boolean isDivision(String s)
    {
        return car(s).equals("/");
    }

    boolean isSquareroot(String s)
    {
        return car(s).equals("sqrt");
    }
    boolean isCosine(String s)
    {
        return car(s).equals("cos");
    }

    boolean isSine(String s)
    {
        return car(s).equals("sin");
    }

    boolean isTan(String s)
    {
        return car(s).equals("tan");
    }

    boolean isAtan(String s)
    {
        return car(s).equals("atan");
    }

    boolean isAcos(String s)
    {
        return car(s).equals("acos");
    }

    boolean isAsin(String s)
    {
        return car(s).equals("asin");
    }

    boolean isSinhyp(String s)
    {
        return car(s).equals("sinh");
    }

    boolean isCoshyp(String s)
    {
        return car(s).equals("cosh");
    }

    boolean isTanhyp(String s)
    {
        return car(s).equals("tanh");
    }

    boolean isLn(String s)
    {
        return car(s).equals("ln");
    }

    boolean isPower(String s)
    {
        return car(s).equals("^");
    }

    boolean isE(String s)
    {
        return car(s).equals("exp");
    }

    boolean isCotan(String s)
    {
        return car(s).equals("cotan");
    }

    boolean isAcotan(String s)
    {
        return car(s).equals("acotan");
    }

    String makeE(String s)
    {
        if(!isConstant(s) && !isVariable(s) && isLn(s))
            return arg1(s);
        if(isConstant(s) && isZero(s))
            return "1";
        else
            return list("exp", s);
    }

    String makeSum(String s, String s1)
    {
        if(isConstant(s) && isConstant(s1))
            return String.valueOf(Double.valueOf(s).doubleValue() + Double.valueOf(s1).doubleValue());
        if(isConstant(s) && isZero(s))
            return s1;
        if(isConstant(s1) && isZero(s1))
            return s;
        if(s.equals(s1))
            return makeProduct("2", s);
        if(!isConstant(s) && !isVariable(s))
        {
            if(isConstant(s1))
                return makeSumSimplifyConstant(s, s1);
            if(isVariable(s1))
                return makeSumSimplifyVariable(s, s1);
            else
                return makeSumSimplifyTwoExpressions(s, s1);
        }
        if(!isConstant(s1) && !isVariable(s1))
        {
            if(isConstant(s))
                return makeSumSimplifyConstant(s1, s);
            if(isVariable(s))
                return makeSumSimplifyVariable(s1, s);
            else
                return makeSumSimplifyTwoExpressions(s1, s);
        } else
        {
            return list("+", s, s1);
        }
    }

    String makeSquareroot(String s)
    {
        if(isConstant(s) && isEven(Math.sqrt(Double.valueOf(s).doubleValue())))
            return String.valueOf(Math.sqrt(Double.valueOf(s).doubleValue()));
        if(!isConstant(s) && !isVariable(s) && isPower(s) && isEven(Double.valueOf(arg2(s)).doubleValue()))
            return makePower(arg1(s), String.valueOf(Double.valueOf(arg2(s)).doubleValue() / 2D));
        else
            return list("sqrt", s);
    }

    String makeProduct(String s, String s1)
    {
        if(isConstant(s) && isConstant(s1))
            return String.valueOf(Double.valueOf(s).doubleValue() * Double.valueOf(s1).doubleValue());
        if(isConstant(s) && isZero(s))
            return "0";
        if(isConstant(s) && isOne(s))
            return s1;
        if(isConstant(s1) && isZero(s1))
            return "0";
        if(isConstant(s1) && isOne(s1))
            return s;
        if(s.equals(s1))
            return makePower(s, "2");
        if(!isConstant(s) && !isVariable(s) && !isConstant(s1) && !isVariable(s1))
            return makeProductSimplifyTwoExp(s, s1);
        if(!isConstant(s) && !isVariable(s))
        {
            if(isConstant(s1))
                return makeProductSimplifyConstant(s, s1);
            if(isVariable(s1))
                return makeProductSimplifyVariable(s, s1);
        } else
        if(!isConstant(s1) && !isVariable(s1))
        {
            if(isConstant(s))
                return makeProductSimplifyConstant(s1, s);
            if(isVariable(s))
                return makeProductSimplifyVariable(s1, s);
        }
        return list("*", s, s1);
    }

    String makeDivision(String s, String s1)
    {
        if(isConstant(s) && isConstant(s1))
        {
            if(Double.valueOf(s1).doubleValue() != 0.0D && isInteger(Double.valueOf(s).doubleValue() / Double.valueOf(s1).doubleValue()))
                return String.valueOf(Double.valueOf(s).doubleValue() / Double.valueOf(s1).doubleValue());
        } else
        {
            if(isConstant(s) && isZero(s))
                return "0";
            if(isConstant(s1) && isOne(s1))
                return s;
            if(s.equals(s1))
                return "1";
            if(!isConstant(s) && !isVariable(s))
            {
                if(isSum(s) || isSubtraction(s))
                    return makeDivisionSimplifyExprThruVar(s, s1);
                if(isDivision(s))
                {
                    if(isVariable(s1) || isConstant(s1))
                        return makeDivision(arg1(s), makeProduct(s1, arg2(s)));
                    if(isDivision(s1))
                        return makeDivision(makeProduct(arg1(s), arg2(s1)), makeProduct(arg2(s), arg1(s1)));
                }
            }
        }
        return list("/", s, s1);
    }

    String makeSubtraction(String s, String s1)
    {
        if(isConstant(s) && isConstant(s1))
            return String.valueOf(Double.valueOf(s).doubleValue() - Double.valueOf(s1).doubleValue());
        if(isConstant(s) && isZero(s))
            return makeProduct("-1", s1);
        if(isConstant(s1) && isZero(s1))
            return s;
        if(s.equals(s1))
            return "0";
        if(!isConstant(s1) && !isVariable(s1) && (isConstant(s) || isVariable(s)))
            return makeSubtractionSimplifyConstantVariableArg1(s, s1);
        if(!isConstant(s) && !isVariable(s) && (isConstant(s1) || isVariable(s1)))
            return makeSubtractionSimplifyConstantVariableArg2(s, s1);
        if(!isConstant(s) && !isVariable(s) && !isConstant(s1) && !isVariable(s1))
            return makeSubtractionSimplifyTwoExp(s, s1);
        else
            return list("-", s, s1);
    }

    String makePower(String s, String s1)
    {
        if(isConstant(s) && isConstant(s1))
        {
            if(isOne(s) || isZero(s1))
                return "1";
            if(isOne(s1))
                return s;
            if(isInteger(Math.pow(Double.valueOf(s).doubleValue(), Double.valueOf(s1).doubleValue())))
                return String.valueOf(Math.pow(Double.valueOf(s).doubleValue(), Double.valueOf(s1).doubleValue()));
        } else
        {
            if(isConstant(s1) && isZero(s1))
                return "1";
            if(isConstant(s1) && isOne(s1))
                return s;
            if(!isConstant(s) && !isVariable(s) && isPower(s) && isConstant(s1) && isConstant(arg2(s)))
                return makePower(arg1(s), makeProduct(arg2(s), s1));
        }
        return list("^", s, s1);
    }

    String makeSine(String s)
    {
        if(!isVariable(s) && !isConstant(s) && isAsin(s))
            return arg1(s);
        if(!isVariable(s) && !isConstant(s) && isAcos(s))
            return makeSquareroot(makeSubtraction("1", makePower(arg1(s), "2")));
        else
            return list("sin", s);
    }

    String makeCosine(String s)
    {
        if(!isVariable(s) && !isConstant(s) && isAcos(s))
            return arg1(s);
        if(!isVariable(s) && !isConstant(s) && isAsin(s))
            return makeSquareroot(makeSubtraction("1", makePower(arg1(s), "2")));
        else
            return list("cos", s);
    }

    String makeTan(String s)
    {
        if(!isVariable(s) && !isConstant(s) && isAtan(s))
            return arg1(s);
        if(!isVariable(s) && !isConstant(s) && isAcotan(s))
            return makeDivision("1", arg1(s));
        else
            return list("tan", s);
    }

    String makeLn(String s)
    {
        if(!isVariable(s) && !isConstant(s) && isE(s))
            return arg1(s);
        else
            return list("ln", s);
    }

    String makeSinhyp(String s)
    {
        return list("sinh", s);
    }

    String makeCoshyp(String s)
    {
        return list("cosh", s);
    }

    String makeTanhyp(String s)
    {
        return list("tanh", s);
    }

    String makeCotan(String s)
    {
        if(!isVariable(s) && !isConstant(s) && isAcotan(s))
            return arg1(s);
        else
            return list("cotan", s);
    }

    String makeAcotan(String s)
    {
        if(!isVariable(s) && !isConstant(s) && isCotan(s))
            return arg1(s);
        else
            return list("acotan", s);
    }

    String makeSumSimplifyConstant(String s, String s1)
    {
        double d = 0.0D;
        if(isSum(s))
        {
            if(isConstant(arg1(s)))
            {
                double d1 = Double.valueOf(s1).doubleValue() + Double.valueOf(arg1(s)).doubleValue();
                if(d1 >= 0.0D)
                    return makeSum(String.valueOf(d1), arg2(s));
                else
                    return makeSubtraction(arg2(s), String.valueOf(-1D * d1));
            }
            if(isConstant(arg2(s)))
            {
                double d2 = Double.valueOf(s1).doubleValue() + Double.valueOf(arg2(s)).doubleValue();
                if(d2 >= 0.0D)
                    return makeSum(String.valueOf(d2), arg1(s));
                else
                    return makeSum(arg1(s), String.valueOf(-1D * d2));
            }
        } else
        if(isSubtraction(s))
        {
            if(isConstant(arg1(s)))
            {
                double d3 = Double.valueOf(s1).doubleValue() + Double.valueOf(arg1(s)).doubleValue();
                if(d3 >= 0.0D)
                    return makeSum(String.valueOf(d3), arg2(s));
                else
                    return makeSubtraction(arg2(s), String.valueOf(-1D * d3));
            }
            if(isConstant(arg2(s)))
            {
                double d4 = Double.valueOf(s1).doubleValue() - Double.valueOf(arg2(s)).doubleValue();
                if(d4 >= 0.0D)
                    return makeSum(String.valueOf(d4), arg1(s));
                else
                    return makeSubtraction(arg1(s), String.valueOf(-1D * d4));
            }
        }
        return list("+", s, s1);
    }

    String makeSumSimplifyVariable(String s, String s1)
    {
        if(isSum(s))
        {
            if(isVariable(arg1(s)) && isSameVariable(s1, arg1(s)))
                return makeSum(makeProduct("2", s1), arg2(s));
            if(isVariable(arg2(s)) && isSameVariable(s1, arg2(s)))
                return makeSum(makeProduct("2", s1), arg1(s));
        } else
        if(isSubtraction(s))
        {
            if(isVariable(arg1(s)) && isSameVariable(s1, arg1(s)))
                return makeSum(makeProduct("2", s1), arg2(s));
            if(isVariable(arg2(s)) && isSameVariable(s1, arg2(s)))
                return arg1(s);
        } else
        if(isProduct(s))
        {
            if(isConstant(arg1(s)) && arg2(s).equals(s1))
                return makeProduct(makeSum("1", arg1(s)), s1);
            if(isConstant(arg2(s)) && arg1(s).equals(s1))
                return makeProduct(makeSum("1", arg2(s)), s1);
        }
        return list("+", s, s1);
    }

    String makeSumSimplifyTwoExpressions(String s, String s1)
    {
        if(isSum(s) && isSum(s1))
        {
            if(arg1(s).equals(arg1(s1)))
                return makeSum(makeProduct("2", arg1(s)), makeSum(arg2(s), arg2(s1)));
            if(arg2(s).equals(arg2(s1)))
                return makeSum(makeProduct("2", arg2(s)), makeSum(arg1(s), arg1(s1)));
            if(arg1(s).equals(arg2(s1)))
                return makeSum(makeProduct("2", arg1(s)), makeSum(arg2(s), arg1(s1)));
            if(arg2(s).equals(arg1(s1)))
                return makeSum(makeProduct("2", arg1(s1)), makeSum(arg1(s), arg2(s1)));
        } else
        if(isSum(s) && isSubtraction(s1))
        {
            if(arg1(s).equals(arg1(s1)))
                return makeSum(makeProduct("2", arg1(s)), makeSubtraction(arg2(s), arg2(s1)));
            if(arg1(s).equals(arg2(s1)))
                return makeSum(arg2(s), arg1(s1));
            if(arg2(s).equals(arg1(s1)))
                return makeSum(makeProduct("2", arg1(s1)), makeSubtraction(arg1(s), arg2(s1)));
            if(arg2(s).equals(arg2(s1)))
                return makeSum(arg1(s), arg1(s1));
        } else
        if(isSum(s) && isProduct(s1))
        {
            if(isConstant(arg1(s1)))
            {
                if(arg1(s).equals(arg2(s1)))
                    return makeSum(arg2(s), makeProduct(makeSum("1", arg1(s1)), arg1(s)));
                if(arg2(s).equals(arg2(s1)))
                    return makeSum(arg1(s), makeProduct(makeSum("1", arg1(s1)), arg2(s)));
            } else
            if(isConstant(arg2(s1)))
            {
                if(arg1(s).equals(arg1(s1)))
                    return makeSum(arg2(s), makeProduct(makeSum("1", arg2(s1)), arg1(s)));
                if(arg2(s).equals(arg1(s1)))
                    return makeSum(arg1(s), makeProduct(makeSum("1", arg2(s1)), arg2(s)));
            }
        } else
        {
            if(isSubtraction(s) && isSum(s1))
                return makeSumSimplifyTwoExpressions(s1, s);
            if(isSubtraction(s) && isSubtraction(s1))
            {
                if(arg1(s).equals(arg1(s1)))
                    return makeSubtraction(makeProduct("2", arg1(s)), makeSum(arg2(s), arg2(s1)));
                if(arg1(s).equals(arg2(s1)))
                    return makeSubtraction(arg1(s1), arg2(s));
                if(arg2(s).equals(arg1(s1)))
                    return makeSubtraction(arg1(s), arg2(s1));
                if(arg2(s).equals(arg2(s1)))
                    return makeSubtraction(makeSum(arg1(s), arg1(s1)), makeProduct("2", arg2(s)));
            } else
            if(isSubtraction(s) && isProduct(s1))
                if(isConstant(arg1(s1)))
                {
                    if(arg1(s).equals(arg2(s1)))
                        return makeSubtraction(makeProduct(makeSum("1", arg1(s1)), arg1(s)), arg2(s));
                    if(arg2(s).equals(arg2(s1)))
                        return makeSum(makeProduct(makeSubtraction(arg1(s1), "1"), arg2(s)), arg1(s));
                } else
                if(isConstant(arg2(s1)))
                {
                    if(arg1(s).equals(arg1(s1)))
                        return makeSubtraction(makeProduct(makeSum("1", arg2(s1)), arg1(s)), arg2(s));
                    if(arg2(s).equals(arg1(s1)))
                        return makeSum(makeProduct(makeSubtraction(arg2(s1), "1"), arg2(s)), arg1(s));
                }
        }
        return list("+", s, s1);
    }

    String makeProductSimplifyVariable(String s, String s1)
    {
        if(isSum(s))
            return makeSum(makeProduct(s1, arg1(s)), makeProduct(s1, arg2(s)));
        if(isSubtraction(s))
            return makeSubtraction(makeProduct(s1, arg1(s)), makeProduct(s1, arg2(s)));
        if(isPower(s) && s1.equals(arg1(s)))
            return makePower(s1, makeSum("1", arg2(s)));
        else
            return list("*", s1, s);
    }

    String makeProductSimplifyTwoExp(String s, String s1)
    {
        if(isSum(s) && isSubtraction(s1) && arg1(s).equals(arg1(s1)) && arg2(s).equals(arg2(s1)))
            return makeSubtraction(makePower(arg1(s), "2"), makePower(arg2(s), "2"));
        if(isSum(s) && isSubtraction(s1) && arg1(s).equals(arg2(s1)) && arg2(s).equals(arg1(s1)))
            return makeSubtraction(makePower(arg2(s), "2"), makePower(arg1(s), "2"));
        if(isSubtraction(s) && isSum(s1) && arg1(s).equals(arg1(s1)) && arg2(s).equals(arg2(s1)))
            return makeSubtraction(makePower(arg1(s), "2"), makePower(arg2(s), "2"));
        if(isSubtraction(s) && isSum(s1) && arg1(s).equals(arg2(s1)) && arg2(s).equals(arg1(s1)))
            return makeSubtraction(makePower(arg1(s), "2"), makePower(arg2(s), "2"));
        else
            return list("*", s, s1);
    }

    String makeProductSimplifyConstant(String s, String s1)
    {
        double d = Double.valueOf(s1).doubleValue();
        if(isSum(s))
        {
            if(d < 0.0D)
                return makeSubtraction(makeProduct(s1, arg1(s)), makeProduct(String.valueOf(-1D * d), arg2(s)));
            if(d > 0.0D)
                return makeSum(makeProduct(s1, arg1(s)), makeProduct(s1, arg2(s)));
            else
                return "0";
        }
        if(isSubtraction(s))
        {
            if(d > 0.0D)
                return makeSubtraction(makeProduct(s1, arg1(s)), makeProduct(s1, arg2(s)));
            if(d < 0.0D)
                return makeSum(makeProduct(s1, arg1(s)), makeProduct(String.valueOf(-1D * d), arg2(s)));
            else
                return "0";
        }
        if(isProduct(s))
        {
            if(isConstant(arg1(s)))
                return makeProduct(String.valueOf(d * Double.valueOf(arg1(s)).doubleValue()), arg2(s));
            if(isConstant(arg2(s)))
                return makeProduct(String.valueOf(d * Double.valueOf(arg2(s)).doubleValue()), arg1(s));
        }
        return list("*", s1, s);
    }
    String makeDivisionSimplifyExprThruVar(String s, String s1)
    {
        if(s1.equals(arg1(s)))
        {
            if(isSum(s))
                return makeSum("1", makeDivision(arg2(s), s1));
            if(isSubtraction(s))
                return makeSubtraction("1", makeDivision(arg2(s), s1));
            if(isProduct(s))
                return makeDivision(arg2(s), s1);
        } else
        if(s1.equals(arg2(s)))
        {
            if(isSum(s))
                return makeSum(makeDivision(arg1(s), s1), "1");
            if(isSubtraction(s))
                return makeSubtraction(makeDivision(arg1(s), s1), "1");
            if(isProduct(s))
                return makeDivision(arg1(s), s1);
        }
        return list("/", s, s1);
    }

    String makeSubtractionSimplifyConstantVariableArg2(String s, String s1)
    {
        if(isConstant(s1))
        {
            if(isSum(s))
            {
                if(isConstant(arg1(s)))
                    return makeSum(String.valueOf(Double.valueOf(arg1(s)).doubleValue() - Double.valueOf(s1).doubleValue()), arg2(s));
                if(isConstant(arg2(s)))
                    return makeSum(String.valueOf(Double.valueOf(arg2(s)).doubleValue() - Double.valueOf(s1).doubleValue()), arg1(s));
            } else
            if(isSubtraction(s))
            {
                if(isConstant(arg1(s)))
                    return makeSum(String.valueOf(Double.valueOf(arg1(s)).doubleValue() - Double.valueOf(s1).doubleValue()), arg2(s));
                if(isConstant(arg2(s)))
                    return makeSum(String.valueOf(-1D * Double.valueOf(arg2(s)).doubleValue() - Double.valueOf(s1).doubleValue()), arg1(s));
            }
        } else
        if(isVariable(s1))
            if(isSum(s))
            {
                if(isVariable(arg1(s)) && isSameVariable(s1, arg1(s)))
                    return arg2(s);
                if(isVariable(arg2(s)) && isSameVariable(s1, arg2(s)))
                    return arg1(s);
            } else
            if(isSubtraction(s))
            {
                if(isVariable(arg1(s)) && isSameVariable(s1, arg1(s)))
                    return makeProduct("-1", arg2(s));
                if(isVariable(arg2(s)) && isSameVariable(s1, arg2(s)))
                    return makeSubtraction(arg1(s), makeProduct("2", arg2(s)));
            } else
            if(isProduct(s))
            {
                if(isConstant(arg1(s)) && arg2(s).equals(s1))
                    return makeProduct(makeSubtraction(arg1(s), "1"), s1);
                if(isConstant(arg2(s)) && arg1(s).equals(s1))
                    return makeProduct(makeSubtraction(arg2(s), "1"), s1);
            }
        return list("-", s, s1);
    }

    String makeSubtractionSimplifyConstantVariableArg1(String s, String s1)
    {
        if(isConstant(s))
        {
            if(isSum(s1))
            {
                if(isConstant(arg1(s1)))
                    return makeSubtraction(String.valueOf(Double.valueOf(arg1(s1)).doubleValue() - Double.valueOf(s).doubleValue()), arg2(s1));
                if(isConstant(arg2(s1)))
                    return makeSubtraction(String.valueOf(Double.valueOf(arg2(s1)).doubleValue() - Double.valueOf(s).doubleValue()), arg1(s1));
            } else
            if(isSubtraction(s1))
            {
                if(isConstant(arg1(s1)))
                    return makeSum(String.valueOf(Double.valueOf(arg1(s1)).doubleValue() - Double.valueOf(s).doubleValue()), arg2(s1));
                if(isConstant(arg2(s1)))
                    return makeSubtraction(String.valueOf(Double.valueOf(s).doubleValue() + Double.valueOf(arg2(s1)).doubleValue()), arg1(s1));
            }
        } else
        if(isVariable(s))
            if(isSum(s1))
            {
                if(isVariable(arg1(s1)) && isSameVariable(s, arg1(s1)))
                    return makeProduct("-1", arg2(s1));
                if(isVariable(arg2(s1)) && isSameVariable(s, arg2(s1)))
                    return makeProduct("-1", arg1(s1));
            } else
            if(isSubtraction(s1))
            {
                if(isVariable(arg1(s1)) && isSameVariable(s, arg1(s1)))
                    return arg2(s1);
                if(isVariable(arg2(s1)) && isSameVariable(s, arg2(s1)))
                    return makeSubtraction(arg1(s1), makeProduct("2", arg2(s1)));
            } else
            if(isProduct(s1))
            {
                if(isConstant(arg1(s1)) && arg2(s1).equals(s))
                    return makeProduct(makeSubtraction("1", arg1(s1)), s);
                if(isConstant(arg2(s1)) && arg1(s1).equals(s))
                    return makeProduct(makeSubtraction("1", arg2(s1)), s);
            }
        return list("-", s, s1);
    }

    String makeSubtractionSimplifyTwoExp(String s, String s1)
    {
        if(isSum(s) && isSum(s1))
        {
            if(arg1(s).equals(arg1(s1)))
                return makeSubtraction(arg2(s), arg2(s1));
            if(arg1(s).equals(arg2(s1)))
                return makeSubtraction(arg2(s), arg1(s1));
            if(arg2(s).equals(arg1(s1)))
                return makeSubtraction(arg1(s), arg2(s1));
            if(arg2(s).equals(arg2(s1)))
                return makeSubtraction(arg1(s), arg1(s1));
        } else
        if(isSum(s) && isSubtraction(s1))
        {
            if(arg1(s).equals(arg1(s1)))
                return makeSum(arg2(s), arg2(s1));
            if(arg1(s).equals(arg2(s1)))
                return makeSum(makeProduct("2", arg1(s)), makeSubtraction(arg2(s), arg1(s1)));
            if(arg2(s).equals(arg1(s1)))
                return makeSum(arg1(s), arg2(s1));
            if(arg2(s).equals(arg2(s1)))
                return makeSum(makeProduct("2", arg2(s)), makeSubtraction(arg1(s), arg1(s1)));
        } else
        if(isSum(s) && isProduct(s1))
        {
            if(isConstant(arg1(s1)))
            {
                if(arg1(s).equals(arg2(s1)))
                    return makeSum(makeProduct(makeSubtraction("1", arg1(s1)), arg1(s)), arg2(s));
                if(arg2(s).equals(arg2(s1)))
                    return makeSum(makeProduct(makeSubtraction("1", arg1(s1)), arg2(s)), arg1(s));
            } else
            if(isConstant(arg2(s1)))
            {
                if(arg1(s).equals(arg1(s1)))
                    return makeSum(makeProduct(makeSubtraction("1", arg2(s1)), arg1(s)), arg2(s));
                if(arg2(s).equals(arg1(s1)))
                    return makeSum(makeProduct(makeSubtraction("1", arg2(s1)), arg2(s)), arg1(s));
            }
        } else
        if(isSubtraction(s) && isSum(s1))
        {
            if(arg1(s).equals(arg1(s1)))
                return makeSubtraction(makeProduct("-1", arg2(s)), arg2(s1));
            if(arg1(s).equals(arg2(s1)))
                return makeSubtraction(makeProduct("-1", arg2(s)), arg1(s1));
            if(arg2(s).equals(arg1(s1)))
                return makeSubtraction(makeSubtraction(arg1(s), arg2(s1)), makeProduct("2", arg1(s1)));
            if(arg2(s).equals(arg2(s1)))
                return makeSubtraction(makeSubtraction(arg1(s), arg1(s1)), makeProduct("2", arg2(s)));
        } else
        if(isSubtraction(s) && isSubtraction(s1))
        {
            if(arg1(s).equals(arg1(s1)))
                return makeSubtraction(arg2(s1), arg2(s));
            if(arg1(s).equals(arg2(s1)))
                return makeSubtraction(makeProduct("2", arg1(s)), makeSum(arg2(s), arg1(s1)));
            if(arg2(s).equals(arg1(s1)))
                return makeSubtraction(makeSum(arg1(s), arg2(s1)), makeProduct("2", arg1(s1)));
            if(arg2(s).equals(arg2(s1)))
                return makeSubtraction(arg1(s), arg1(s1));
        } else
        if(isSubtraction(s) && isProduct(s1))
            if(isConstant(arg1(s1)))
            {
                if(arg1(s).equals(arg2(s1)))
                    return makeSubtraction(makeProduct(makeSubtraction("1", arg1(s1)), arg1(s)), arg2(s));
                if(arg2(s).equals(arg2(s1)))
                    return makeSubtraction(makeProduct(makeSubtraction("-1", arg1(s1)), arg2(s)), arg1(s));
            } else
            if(isConstant(arg2(s1)))
            {
                if(arg1(s).equals(arg1(s1)))
                    return makeSubtraction(makeProduct(makeSubtraction("1", arg2(s1)), arg1(s)), arg2(s));
                if(arg2(s).equals(arg1(s1)))
                    return makeSubtraction(makeProduct(makeSubtraction("-1", arg2(s1)), arg2(s)), arg1(s));
            }
        return list("-", s, s1);
    }

    String Derive(String s, String s1)
    {
        if(isConstant(s))
            return "0";
        if(isVariable(s))
            return deriveVariable(s, s1);
        if(isSum(s))
            return deriveSum(s, s1);
        if(isSubtraction(s))
            return deriveSubtraction(s, s1);
        if(isProduct(s))
            return deriveProduct(s, s1);
        if(isDivision(s))
            return deriveDivision(s, s1);
        if(isSquareroot(s))
            return deriveSqrt(s, s1);
        if(isSine(s))
            return deriveSin(s, s1);
        if(isCosine(s))
            return deriveCos(s, s1);
        if(isTan(s))
            return deriveTan(s, s1);
        if(isPower(s))
            return derivePower(s, s1);
        if(isLn(s))
            return deriveLn(s, s1);
        if(isE(s))
            return deriveE(s, s1);
        if(isAtan(s))
            return deriveAtan(s, s1);
        if(isAsin(s))
            return deriveAsin(s, s1);
        if(isAcos(s))
            return deriveAcos(s, s1);
        if(isSinhyp(s))
            return deriveSinhyp(s, s1);
        if(isCoshyp(s))
            return deriveCoshyp(s, s1);
        if(isTanhyp(s))
            return deriveTanhyp(s, s1);
        if(isCotan(s))
            return deriveCotan(s, s1);
        if(isAcotan(s))
            return deriveAcotan(s, s1);
        else
            return "";
    }

    String deriveAcotan(String s, String s1)
    {
        return makeDivision(makeProduct("-1", Derive(arg1(s), s1)), makeSum("1", makePower(arg1(s), "2")));
    }

    String deriveCotan(String s, String s1)
    {
        return makeProduct(Derive(arg1(s), s1), makeSubtraction("-1", makePower(makeCotan(arg1(s)), "2")));
    }

    String deriveTanhyp(String s, String s1)
    {
        return makeProduct(Derive(arg1(s), s1), makeSubtraction("1", makePower(makeTanhyp(arg1(s)), "2")));
    }

    String deriveCoshyp(String s, String s1)
    {
        return makeProduct(Derive(arg1(s), s1), makeSinhyp(arg1(s)));
    }

    String deriveSinhyp(String s, String s1)
    {
        return makeProduct(Derive(arg1(s), s1), makeCoshyp(arg1(s)));
    }

    String deriveAcos(String s, String s1)
    {
        return makeProduct("-1", makeDivision(Derive(arg1(s), s1), makeSquareroot(makeSubtraction("1", makePower(arg1(s), "2")))));
    }

    String deriveAsin(String s, String s1)
    {
        return makeDivision(Derive(arg1(s), s1), makeSquareroot(makeSubtraction("1", makePower(arg1(s), "2"))));
    }

    String deriveAtan(String s, String s1)
    {
        return makeDivision(Derive(arg1(s), s1), makeSum("1", makePower(arg1(s), "2")));
    }

    String deriveE(String s, String s1)
    {
        return makeProduct(Derive(arg1(s), s1), makeE(arg1(s)));
    }

    String deriveLn(String s, String s1)
    {
        return makeProduct(Derive(arg1(s), s1), makeDivision("1", arg1(s)));
    }

    String deriveTan(String s, String s1)
    {
        return makeProduct(Derive(arg1(s), s1), makeSum("1", makePower(makeTan(arg1(s)), "2")));
    }

    String deriveSin(String s, String s1)
    {
        return makeProduct(Derive(arg1(s), s1), makeCosine(arg1(s)));
    }

    String deriveCos(String s, String s1)
    {
        return makeProduct(Derive(arg1(s), s1), makeProduct("-1", makeSine(arg1(s))));
    }

    String deriveSqrt(String s, String s1)
    {
        return makeDivision(Derive(arg1(s), s1), makeProduct("2", makeSquareroot(arg1(s))));
    }

    String deriveDivision(String s, String s1)
    {
        return makeDivision(makeSubtraction(makeProduct(arg2(s), Derive(arg1(s), s1)), makeProduct(arg1(s), Derive(arg2(s), s1))), makePower(arg2(s), "2"));
    }

    String deriveProduct(String s, String s1)
    {
        return makeSum(makeProduct(arg1(s), Derive(arg2(s), s1)), makeProduct(Derive(arg1(s), s1), arg2(s)));
    }

    String deriveSubtraction(String s, String s1)
    {
        return makeSubtraction(Derive(arg1(s), s1), Derive(arg2(s), s1));
    }

    String deriveSum(String s, String s1)
    {
        return makeSum(Derive(arg1(s), s1), Derive(arg2(s), s1));
    }

    String derivePower(String s, String s1)
    {
        if(!isConstant(arg1(s)))
        {
            if(isConstant(arg2(s)))
                return makeProduct(Derive(arg1(s), s1), makeProduct(arg2(s), makePower(arg1(s), makeSubtraction(arg2(s), "1"))));
            else
                return makeProduct(s, makeSum(makeProduct(Derive(arg2(s), s1), makeLn(arg1(s))), makeProduct(Derive(makeLn(arg1(s)), s1), arg2(s))));
        } else
        {
            return makeProduct(makeProduct(makeLn(arg1(s)), Derive(arg2(s), s1)), s);
        }
    }

    String deriveVariable(String s, String s1)
    {
        if(isSameVariable(s, s1))
            return "1";
        else
            return "0";
    }

    String Simplify(String s)
    {
        if(isConstant(s))
            if(isInteger(Double.valueOf(s).doubleValue()))
                return String.valueOf(Double.valueOf(s).intValue());
            else
                return s;
        if(isVariable(s))
            return s;
        if(isSum(s))
            return makeSum(Simplify(arg1(s)), Simplify(arg2(s)));
        if(isSubtraction(s))
            return makeSubtraction(Simplify(arg1(s)), Simplify(arg2(s)));
        if(isProduct(s))
            return makeProduct(Simplify(arg1(s)), Simplify(arg2(s)));
        if(isDivision(s))
            return makeDivision(Simplify(arg1(s)), Simplify(arg2(s)));
        if(isSquareroot(s))
            return makeSquareroot(Simplify(arg1(s)));
        if(isSine(s))
            return makeSine(Simplify(arg1(s)));
        if(isCosine(s))
            return makeCosine(Simplify(arg1(s)));
        if(isTan(s))
            return makeTan(Simplify(arg1(s)));
        if(isPower(s))
            return makePower(Simplify(arg1(s)), Simplify(arg2(s)));
        if(isLn(s))
            return makeLn(Simplify(arg1(s)));
        if(isE(s))
            return makeE(Simplify(arg1(s)));
        if(isSinhyp(s))
            return makeSinhyp(Simplify(arg1(s)));
        if(isCoshyp(s))
            return makeCoshyp(Simplify(arg1(s)));
        if(isTanhyp(s))
            return makeTanhyp(Simplify(arg1(s)));
        if(isCotan(s))
            return makeCotan(Simplify(arg1(s)));
        if(isAcotan(s))
            return makeAcotan(Simplify(arg1(s)));
        else
            return s;
    }

    String SimplifyAsMuchAsPossible(String s)
    {
        String s1 = "";
        String s2 = "";
        s2 = s;
        do
        {
            s2 = Simplify(s2);
            if(!s1.equalsIgnoreCase(s2))
                s1 = s2;
            else
                return s2;
        } while(true);
    }

    String firstOp(String s)
    {
        return car(s);
    }

    String preToInfix(String s)
    {
        if(isVariable(s) || isConstant(s))
            return s;
        String s1 = firstOp(s);
        String s2 = arg1(s);
        String s3 = "";
        if(!isTwoArgOp(s1))
            return s1 + "(" + preToInfix(s2) + ")";
        s3 = arg2(s);
        if(isConstant(s2) || isVariable(s2))
        {
            if(isConstant(s3) || isVariable(s3))
                return s2 + s1 + s3;
            if(s1.equalsIgnoreCase("+"))
                return s2 + s1 + preToInfix(s3);
            if(s1.equalsIgnoreCase("-") && (isDivision(s3) || isProduct(s3)))
                return s2 + s1 + preToInfix(s3);
            if(s1.equalsIgnoreCase("*") && (isPower(s3) || isProduct(s3) || !isTwoArgOp(firstOp(s3))))
                return s2 + s1 + preToInfix(s3);
            if(isTwoArgOp(firstOp(s3)))
                return s2 + s1 + "(" + preToInfix(s3) + ")";
            else
                return s2 + s1 + preToInfix(s3);
        }
        if(isConstant(s3) || isVariable(s3))
        {
            if(s1.equalsIgnoreCase("+"))
                return preToInfix(s2) + s1 + s3;
            if(s1.equalsIgnoreCase("-"))
                return preToInfix(s2) + s1 + s3;
            if(isTwoArgOp(firstOp(s2)))
                return "(" + preToInfix(s2) + ")" + s1 + s3;
            else
                return preToInfix(s2) + s1 + s3;
        }
        if(s1.equalsIgnoreCase("+"))
            return preToInfix(s2) + s1 + preToInfix(s3);
        if(s1.equalsIgnoreCase("-"))
            if(isProduct(s3) || isDivision(s3))
                return preToInfix(s2) + s1 + preToInfix(s3);
            else
                return preToInfix(s2) + s1 + "(" + preToInfix(s3) + ")";
        if(isTwoArgOp(firstOp(s2)) && isTwoArgOp(firstOp(s3)))
            return "(" + preToInfix(s2) + ")" + s1 + "(" + preToInfix(s3) + ")";
        if(isTwoArgOp(firstOp(s2)) && !isTwoArgOp(firstOp(s3)))
            return "(" + preToInfix(s2) + ")" + s1 + preToInfix(s3);
        if(isTwoArgOp(firstOp(s3)) && !isTwoArgOp(firstOp(s2)))
            return preToInfix(s2) + s1 + "(" + preToInfix(s3) + ")";
        else
            return preToInfix(s2) + s1 + preToInfix(s3);
    }

    String InToPrefix(String s)
        throws Exception
    {
        String s1;
        String s7;
        String s10;
        String s2 = s7 = s10 = s1 = "";
        int i;
        int j = i = 0;
        if(s == "")
            throw new Exception("Wrong number of arguments to operator");
        if(isVariable(s))
        {
            storeVars(s);
            return s;
        }
        if(isAllNumbers(s))
            return s;
        if(s.charAt(0) == '(' && (j = Match(s, 0)) == s.length() - 1)
            return InToPrefix(s.substring(1, j));
        while(i < s.length()) 
        {
            String s11;
            if((s11 = getOp(s, i)) != null)
            {
                if(isTwoArgOp(s11))
                {
                    String s3;
                    if(s11.equalsIgnoreCase("+") || s11.equalsIgnoreCase("-"))
                    {
                        if(s1 == "")
                            s1 = "0";
                        s3 = ArgToPlusOrMinus(s, i + 1);
                    } else
                    if(s11.equalsIgnoreCase("*") || s11.equalsIgnoreCase("/"))
                    {
                        if(s1 == "")
                            throw new Exception("Wrong number of arguments to operator");
                        s3 = ArgToAnyOpExcept(s, i + 1, "^");
                    } else
                    {
                        if(s1 == "")
                            throw new Exception("Wrong number of arguments to operator");
                        s3 = Arg(s, i + s11.length());
                    }
                    s1 = "( " + s11 + " " + s1 + " " + InToPrefix(s3) + " )";
                    i += s11.length() + s3.length();
                } else
                {
                    String s4 = Arg(s, i + s11.length());
                    s1 = s1 + "( " + s11 + " " + InToPrefix(s4) + " )";
                    i += s11.length() + s4.length();
                }
            } else
            {
                String s5 = Arg(s, i);
                s11 = getOp(s, i + s5.length());
                if(s11 == null)
                    throw new Exception("Missing operator");
                if(isTwoArgOp(s11))
                {
                    String s8;
                    if(s11.equalsIgnoreCase("+") || s11.equalsIgnoreCase("-"))
                        s8 = ArgToPlusOrMinus(s, i + 1 + s5.length());
                    else
                    if(s11.equalsIgnoreCase("*") || s11.equalsIgnoreCase("/"))
                        s8 = ArgToAnyOpExcept(s, i + 1 + s5.length(), "^");
                    else
                        s8 = Arg(s, i + s11.length() + s5.length());
                    s1 = s1 + "( " + s11 + " " + InToPrefix(s5) + " " + InToPrefix(s8) + " )";
                    i += s5.length() + s8.length() + s11.length();
                } else
                {
                    s1 = s1 + "( " + s11 + " " + InToPrefix(s5) + " )";
                    i += s11.length() + s5.length();
                }
            }
            String s9;
            String s6 = s11 = s9 = "";
        }
        return s1;
    }

    String Arg(String s, int i)
    {
        boolean flag = false;
        int k = i;
        String s1 = "";
        String s2 = "";
        while(k < s.length()) 
            if(s.charAt(k) == '(')
            {
                int j = Match(s, k);
                s2 = s2 + s.substring(k, j + 1);
                k = j + 1;
            } else
            if((s1 = getOp(s, k)) != null)
            {
                if(s2 != "" && !isTwoArgOp(BackTrack(s2)))
                    return s2;
                s2 = s2 + s1;
                k += s1.length();
            } else
            {
                s2 = s2 + s.charAt(k);
                k++;
            }
        return s2;
    }

    String ArgToAnyOpExcept(String s, int i, String s1)
    {
        boolean flag = false;
        int k = i;
        String s2 = "";
        String s3 = "";
        while(k < s.length()) 
            if(s.charAt(k) == '(')
            {
                int j = Match(s, k);
                s3 = s3 + s.substring(k, j + 1);
                k = j + 1;
            } else
            if((s2 = getOp(s, k)) != null)
            {
                if(s3 != "" && !isTwoArgOp(BackTrack(s3)) && !s2.equalsIgnoreCase(s1))
                    return s3;
                s3 = s3 + s2;
                k += s2.length();
            } else
            {
                s3 = s3 + s.charAt(k);
                k++;
            }
        return s3;
    }

    String ArgToPlusOrMinus(String s, int i)
    {
        boolean flag = false;
        int k = i;
        String s1 = "";
        for(; k < s.length(); k++)
            if(s.charAt(k) == '(')
            {
                int j = Match(s, k);
                s1 = s1 + s.substring(k, j + 1);
                k = j;
            } else
            if((s.charAt(k) == '+' || s.charAt(k) == '-') && s1 != "")
            {
                if(isTwoArgOp(BackTrack(s1)))
                    s1 = s1 + s.charAt(k);
                else
                    return s1;
            } else
            {
                s1 = s1 + s.charAt(k);
            }

        return s1;
    }

    String BackTrack(String s)
    {
        boolean flag = false;
        String s1 = "";
        try
        {
            for(int i = 0; i <= maxoplength; i++)
            {
                String s2;
                if((s2 = getOp(s, (s.length() - 1 - maxoplength) + i)) != null && (s.length() - maxoplength - 1) + i + s2.length() == s.length())
                    return s2;
            }

        }
        catch(Exception _ex) { }
        return null;
    }

    String getOp(String s, int i)
    {
        boolean flag = false;
        for(int j = 0; j < maxoplength; j++)
            try
            {
                if(isOperator(s.substring(i, i + (maxoplength - j))))
                    return s.substring(i, i + (maxoplength - j));
            }
            catch(Exception _ex) { }

        return null;
    }

    String PrepareExp(String s)
    {
        String s1 = "";
        s1 = SkipSpaces(s);
        s1 = s1.toLowerCase();
        if(s1.charAt(0) == '+' || s1.charAt(0) == '-')
            s1 = "0" + s1;
        return parseE(s1);
    }

    String parseE(String s)
    {
        boolean flag = false;
        String s1 = "";
        String s2 = "";
        for(int i = 0; i < s.length(); i++)
            try
            {
                if(s.charAt(i) == 'e' && isConstant(s.charAt(i - 1)))
                {
                    String s3 = Arg(s, i + 1);
                    if(s3.charAt(s3.length() - 1) == ')')
                        s3 = s3.substring(0, s3.indexOf(")"));
                    if(isAllNumbers(s3))
                        s1 = s1 + "*10^";
                    else
                        s1 = s1 + s.charAt(i);
                } else
                {
                    s1 = s1 + s.charAt(i);
                }
            }
            catch(Exception _ex)
            {
                s1 = s1 + s.charAt(i);
            }

        return s1;
    }

    String SkipSpaces(String s)
    {
        String s1 = "";
        boolean flag = false;
        for(int i = 0; i < s.length(); i++)
            if(s.charAt(i) != ' ')
                s1 = s1 + s.charAt(i);

        return s1;
    }
/************** stopped here *************************/

    String parseSigns(String s)
    {
        String s1 = "";
        for(int i = 0; i < s.length(); i++)
            try
            {
                if(s.charAt(i) == '+' && s.charAt(i + 1) == '+')
                {
                    s1 = s1 + "+";
                    i++;
                } else
                if(s.charAt(i) == '+' && s.charAt(i + 1) == '-')
                {
                    s1 = s1 + "-";
                    i++;
                } else
                if(s.charAt(i) == '-' && s.charAt(i + 1) == '+')
                {
                    s1 = s1 + "-";
                    i++;
                } else
                if(s.charAt(i) == '-' && s.charAt(i + 1) == '-')
                {
                    s1 = s1 + "+";
                    i++;
                } else
                {
                    s1 = s1 + s.charAt(i);
                }
            }
            catch(Exception _ex) { }

        return s1;
    }

    int Match(String s, int i)
    {
        int j = i;
        int k = 0;
        for(; j < s.length(); j++)
        {
            if(s.charAt(j) == '(')
                k++;
            else
            if(s.charAt(j) == ')')
                k--;
            if(k == 0)
                return j;
        }

        return i;
    }

    String findVariable(String s)
    {
        int i = 0;
        String s1 = "";
        if(lastIndex >= s.length())
        {
            lastIndex = 0;
            return null;
        }
        i = s.indexOf(";", lastIndex);
        if(i == -1)
        {
            String s2 = s.substring(lastIndex, s.length());
            lastIndex = s.length();
            return s2;
        } else
        {
            String s3 = s.substring(lastIndex, i);
            lastIndex = i + 1;
            return s3;
        }
    }

    public static void main(String args[])
    {
        GUIDerive guiderive = new GUIDerive();
        GUIDerive.main(new String[2]);
    }

    String[] doubleAndCopyArray(String as[])
    {
        boolean flag = false;
        int j = as.length;
        String as1[] = new String[j * 2];
        for(int i = 0; i < j; i++)
            as1[i] = as[i];

        return as1;
    }

    void storeVars(String s)
    {
        if(!tempstorage.contains(s))
        {
            if(VARIABLES == "")
            {
                VARIABLES = s;
                storedvars = s;
            } else
            {
                VARIABLES += ";" + s;
                storedvars += ";" + s;
            }
            tempstorage.addElement(s);
        }
    }

    void clearVars()
    {
        tempstorage.removeAllElements();
        VARIABLES = "";
        storedvars = "";
    }

    public String[] diff(String s, String s1)
        throws notValidSyntaxException
    {
        String s2 = "";
        String s4 = "";
        String s6 = "";
        String s7 = "";
        String s9 = "";
        String s11 = "";
        String as[] = new String[100];
        int i = 0;
        if(s == null || s.equals(""))
            throw new notValidSyntaxException("Arguments null or empty string");
        clearVars();
        s6 = PrepareExp(s);
        try
        {
            Syntax(s6);
            String s10 = InToPrefix(s6);
            s10 = SimplifyAsMuchAsPossible(s10);
            String s3;
            if(s1 == null || s1.equals(""))
            {
                s3 = storedvars != "" ? storedvars : defaultvar;
                s3 = SkipSpaces(s3.toLowerCase());
            } else
            {
                s3 = SkipSpaces(s1.toLowerCase());
            }
            String s5;
            while((s5 = findVariable(s3)) != null) 
            {
                Syntax(s5);
                if(!isVariable(s5))
                    throw new Exception("Not a valid variable " + s5);
                String s12 = Derive(s10, s5);
                s12 = SimplifyAsMuchAsPossible(s12);
                String s8 = preToInfix(s12);
                if(i > as.length - 1)
                    as = doubleAndCopyArray(as);
                as[i] = parseSigns(s8);
                i++;
            }
            return as;
        }
        catch(StringIndexOutOfBoundsException _ex)
        {
            throw new notValidSyntaxException("Wrong number of arguments to operator");
        }
        catch(Exception exception)
        {
            throw new notValidSyntaxException(exception.getMessage());
        }
    }

    public String[] diff(String s)
        throws notValidSyntaxException
    {
        return diff(s, "");
    }

    String allowedops[] = {
        "^", "+", "-", "/", "*", "cos", "sin", "exp", "ln", "tan", 
        "acos", "asin", "atan", "cosh", "sinh", "tanh", "sqrt", "cotan", "acotan"
    };
    String twoargops[] = {
        "^", "+", "-", "/", "*"
    };
    int maxoplength;
    public String VARIABLES;
    String storedvars;
    String defaultvar;
    int lastIndex;
    Vector tempstorage;
}

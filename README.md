Spring AOP
==========

There is defined an @Authenticated annotation, for which there needs to be created and applied the SecurityAspect.

For this to happen, you need to:

1. in aop-config.xml enable @AspectJ support

2. make SecurityAspect class as aspect

3. make SecurityAspect class candidate for bean detection

4. annotate TransferServiceImpl#transferAmount(...) with @Authenticated

5. define SecurityAspect#secure() as advice and also define it's pointcut that should intercept all methods annotated with @Authenticated

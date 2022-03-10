# ShoppingCart
Equal Experts project

The requirements didn't mention sales tax rounding across line items or internal storage so I'm describing my two assumptions.
For sales tax and totals, I went for accuracy by applying the rounding at the final total step.  Given the acceptance tests,
the sales tax total and final total pass.  There could be issues with items that are priced in fractions of a cent.
Actual requirements would of course be based on the relevant laws.
From my experience with POS systems and the Stripe payment system, all costs are stored as integers with a (currently) fixed
scale.  This fits the requirements as I understood them, but I'm bringing it up to avoid any confusion in case I misinterpreted them.

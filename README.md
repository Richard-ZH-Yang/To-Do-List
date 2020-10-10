# Fight the Landlord Card Game

## What will the application do?
The application will create a three players card game. For the current phase, the game will only have a player that is 
controlled by the user, and the computer will control the other two players. Maybe later, the multiplayer option will be added, so that three players can play together.
When the game starts, each player will have 17 cards, and they can claim to be the landlord. Once the landlord is specified, 
the landlord will have three additional cards. Starts from the landlord, the game will go counterclockwise, each
player gets a chance to play their card. If one has a play that others cannot take, one individual could start from him
 again. Whoever plays all their cards first who will be the winner of the game. This game uses a virtual currency called
Happy Coin, and the bid for each player will double by certain play.

These are different valid plays:
- Single card - ranking from three (low) up to red joker (high) as explained above
- Pair - two cards of the same rank, from three (low) up to two (high)
- Triplet - three cards of the same rank
- Triplet with an attached card - a triplet with any single card added, for example 6-6-6-8. These rank according to the 
rank of the triplet - so for example 9-9-9-3 beats 8-8-8-A.
- Triplet with an attached pair - a triplet with a pair added, like a full house in poker, the ranking being determined 
by the rank of the triplet - for example Q-Q-Q-6-6 beats 10-10-10-K-K.
- Sequence - at least five cards of consecutive rank, from 3 up to ace - for example 8-9-10-J-Q. Twos and jokers cannot be used.
- Sequence of pairs - at least three pairs of consecutive ranks, from 3 up to ace. Twos and jokers cannot be used. 
For example 10-10-J-J-Q-Q-K-K.
- Sequence of triplets - at least two triplets of consecutive ranks from three up to ace. For example 4-4-4-5-5-5.
- Sequence of triplets with attached cards - an extra card is added to each triplet. For example 7-7-7-8-8-8-3-6. 
The attached cards must be different from all the triplets and from each other. Although triplets of twos cannot be 
included, a two or a joker or one of each can be attached, but not both jokers.
- Sequence of triplets with attached pairs - an extra pair is attached to each triplet. Only the triplets have to be in
 sequence - for example 8-8-8-9-9-9-4-4-J-J. The pairs must be different in rank from each other and from all the triplets. 
 Although triplets of twos cannot be included, twos can be attached. Note that attached single cards and attached pairs 
 cannot be mixed - for example 3-3-3-4-4-4-6-7-7 is not valid.
- Bomb - four cards of the same rank. A bomb can beat everything except a rocket, and a higher ranked bomb can beat 
a lower ranked one.
- Rocket - a pair of jokers. It is the highest combination and beats everything else, including bombs.
- Quadplex set - there are two types: a quad with two single cards of different ranks attached, such as 6-6-6-6-8-9, or 
a quad with two pairs of different ranks attached, such as J-J-J-J-9-9-Q-Q. Twos and jokers can be attached, 
but you cannot use both jokers in one quadplex set. Quadplex sets are ranked according to the rank of the quad. Note that a quadplex set can only beat a lower quadplex set of the same type, and cannot beat any other type of combination. Also a quadplex set can be beaten by a bomb made of lower ranked cards.
#### More Rules for the game:
**rules reference**: https://www.pagat.com/climbing/doudizhu.html

## Who will use it?
Fight the Landlord is one of **China**'s most popular card games; therefore, the potential user base is relatively large.
But for now, this game is only designed for a **single player** or **multiplayer** in the same network. 

## Why is this project of interest to me?
I have wanted to design this game for a long time. I used to play this game with my friends or on my mobile phone, 
I believe it is an exciting game as there could be many potentials of plays, and the biding makes the game more entertaining.
When I was playing the game, I was thinking in my head how the game worked and calculated the possibility for each play.
For example, what is the possibility that my opponent has a bomb, which is four cards with the same number, etc.


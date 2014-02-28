Homework1:
Create a program to generate the following information.
1. The number of tokens in the Cranfield text collection;
2. The number of unique words in the Cranfield text collection ;
3. The number of words that occur only once in the Cranfield text collection;
4. The 30 most frequent words in the Cranfield text collection – list them and their
respective frequency information; and
5. The average number of word tokens per document.

Homework2 (Indexing):

For every term that is indexed, store:
- Document frequency (df): The number of documents that the term occurs in.
- Term frequency (tf): The number of times that the term occurs in
each document, and
- The list of documents containing the term.
For each document, store the frequency of the most frequent stem in that document
(max_tf), and the total number of word occurrences in the document, including stopwords
(doclen).
Store the inverted lists in your own storage manager. Also, in a second version of your
index, compress the inverted lists before storing them, using delta encoding for the
document-id and gamma code for the frequency information. A penalty of -100 points
will be applied if you do not have also a version of the compressed index, obtaining only
max 100 points in this homework.
Delta codes are similar to the gamma codes: they represent a gap by a pair: (length,
offset). First the number is represented in binary code. The length of the binary
representation is encoded in gamma code, prior to removing the leading 1-bit. After
generating the code of the length only, the leading 1-bit is removed and represented in
gamma code.

Homework3:
In this assignment you will implement a simple statistical retrieval system, using the
inverted list index that you built in the last assignment. 
The retrieval system must read a query, index it (i.e., parse it, discard stop-words, stem
terms, etc), and then determine scores for documents by summing the tf.idf weights for
every matching query-document term pair.
Implement and compare two tf.idf term weighting functions.
W1 = (0.4 + 0.6 * log (tf + 0.5) / log (maxtf + 1.0))
* (log (collectionsize / df)/ log (collectionsize))
W2 = (0.4 + 0.6 * (tf / (tf + 0.5 + 1.5 *
(doclen / avgdoclen))) * log (collectionsize / df)/
log (collectionsize))
tf: the frequency of the term in the document,
maxtf: the frequency of the most frequent indexed
term in the document,
df: the number of documents containing the
term,
doclen: the length of the document, in words,
counting stopwords,
avgdoclen: the average document length in the
collection, and
collectionsize: the number of documents in the collection.
W1 is a variation of older, but well-known, 'max tf' term weighting. W2 is a variation on
Okapi term weighting. Both TW1 and TW2 use a fairly standard scaled idf.
Documents should be presented in ranked order of the total scores.

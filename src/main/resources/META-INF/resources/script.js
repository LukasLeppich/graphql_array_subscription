
console.log('--- start ---');
const createClient = this.graphqlWs.createClient;



const client = createClient({
    url: 'ws://localhost:8080/graphql'
})



console.log('-- subscribe ---');
const unsubscribe = client.subscribe({
    query: `subscription sub {
  scalarSubscription {
    value
  }
}`
},
    {
        next: (data) => console.log('DATA: ', data),
        error: (err) => console.error(err),
        complete: () => console.log('COMPLETE')
    }
);
setTimeout(() => {
    unsubscribe();
}, 5000);
console.log('-- finished ---');

% Define facts
habitat(dog, land).
habitat(cat, land).
habitat(human, land).
habitat(snake, land).
habitat(fish, water).
habitat(dolphin, water).
habitat(eagle, air).
habitat(bat, air).

% Define rules
land_animal(X) :-
    habitat(X, land).

flying_animal(X) :-
    habitat(X, air).

water_animal(X) :-
    habitat(X, water).

% Define predicate for user interaction
identify_animal_category :-
    write('Welcome to the Animal Classifier'), nl,
    write('Please enter the name of an animal to identify its category'), nl,
    ask_animal.

% Ongoing classification and user interaction loop
ask_animal :-
    write('Animal name: '),
    read(Animal),
    (Animal == quit ->
        true
    ;
        classify(Animal),
        write('To continue, enter another animal name or type "quit" to exit'), nl,
        ask_animal).

% Define rule to identify
classify(Animal) :-
    (land_animal(Animal) ->
        write(Animal), write(' is a land animal'), nl
    ; flying_animal(Animal) ->
        write(Animal), write(' is a flying animal'), nl
    ; water_animal(Animal) ->
        write(Animal), write(' is a water animal'), nl
    ;
        write(Animal), write(' is not a known animal'), nl).

main :-
    identify_animal_category.